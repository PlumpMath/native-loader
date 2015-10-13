(ns test-native-loader
  (:require [coldnew.native-loader :refer :all])
  (:import [org.opencv.core Mat Size CvType]
           [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc])
  (:gen-class))

;; load libraries from opencv-native package
(load-library org.opencv.core.Core/NATIVE_LIBRARY_NAME)

(defn -main []
  (let [lena (Imgcodecs/imread (load-file-from-jar "lena.png"))
        ;;(str (clojure.java.io/resource "lena.png")))
        buffer (Mat. 512 512 CvType/CV_8UC3)]

    (println lena)
    (Imgproc/cvtColor lena buffer Imgproc/COLOR_RGB2GRAY)
    (Imgproc/equalizeHist buffer buffer)
    ))