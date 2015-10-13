(defproject org.clojars.coldnew/native-loader "0.1.0-SNAPSHOT"
  :description " Native library loader for extracting and loading native libraries from jar."
  :url "https://github.com/coldnew/native-loader"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]

  :source-paths ["src/clj"]

  :profiles {:uberjar-test
             {:dependencies [[org.clojars.coldnew/opencv "3.0.0"]
                             [org.clojars.coldnew/opencv-native-macosx-x86_64 "3.0.0-SNAPSHOT"]]
              :injections [(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)]
              :source-paths ["test/clj"]
              :resource-paths ["dev-resources"]
              :uberjar-name "native-loader-test.jar"
              :aot :all}
             })