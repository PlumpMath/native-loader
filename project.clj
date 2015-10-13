(defproject org.coldnew/native-loader "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]

  :source-paths ["src/clj"]

  :profiles {:uberjar-test
             {:dependencies [[opencv/opencv "3.0.0"]
                             [opencv/opencv-native "3.0.0"]]
              :injections [(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)]
              :source-paths ["test/clj"]
              :resource-paths ["dev-resources"]
              :aot :all}
             })

;; lein with-profiles uberjar-test  uberjar
;; java -jar target/native-loader-0.1.0-SNAPSHOT-standalone.jar -m test-native-loader