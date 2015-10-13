(ns native-library-loader.core
  (:require [clojure.string :as str]
            [clojure.java.io :as io])
  (:import [java.io File FileOutputStream])
  (:gen-class))

;;; Reference:
;; https://github.com/circleci/clj-v8/blob/e22fc83af27db06d338f2924fa1c3212b0d1a84b/clj-v8/src/v8/core.clj
;; https://github.com/adamheinrich/native-utils/blob/master/NativeUtils.java
;; https://github.com/scijava/native-lib-loader
;; https://github.com/swannodette/native-deps

(defn- find-library-path
  []
  (let [os-name (System/getProperty "os.name")
        os-arch (System/getProperty "os.arch")]
    (case [os-name os-arch]
      ["Mac OS X" "x86_64"] "macosx/x86_64/"
      ["Linux" "x86_64"]    "linux/x86_64/"
      ["Linux" "amd64"]     "linux/x86_64/"
      ["Linux" "x86"]       "linux/x86/"
      ["Solaris" "x86"]     "solaris/x86/"
      ["Solaris" "x86_64"]  "solaris/x86_64/"
      (throw (Exception. (str "Unsupported OS/archetype: " os-name " " os-arch))))))

(defn- find-library-native-path []
  (str "native/" (find-library-path)))

(defn- load-resource
  [path]
  (let [thr (Thread/currentThread)
        ldr (.getContextClassLoader thr)]
    (.getResourceAsStream ldr path)))

(defn load-library-from-jar [name]
  (let [binary-path (find-library-native-path)
        lib (System/mapLibraryName name)
        tmp (File/createTempFile lib "")
        in (load-resource (str binary-path lib))
        out (FileOutputStream. tmp)]

    (io/copy in out)
    (.close out)
    (.close in)

    ;; load the library and remove tmp file on exit
    (System/load (.getAbsolutePath tmp))
    (.deleteOnExit tmp)))

(defn load-library [name]
  (try
    (clojure.lang.RT/loadLibrary name)
    (catch UnsatisfiedLinkError e
      (try
        (load-library-from-jar name)
        (catch Exception e
          (.printStackTrace e))
        ))))