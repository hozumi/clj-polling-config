(ns hozumi.polling-config
  (:require [clojure.java.io :as javaio]))

(defn run-background
  ([file interval-msec]
     (run-background file interval-msec (ref nil)))
  ([file interval-msec conf-ref]
     (let [file (javaio/file file)
           last-modified (ref 0)]
       (future
        (loop []
          (let [current-last-modified (.lastModified file)]
            (when (> current-last-modified @last-modified)
              (dosync
               (ref-set last-modified current-last-modified)
               (ref-set conf-ref (-> file slurp read-string)))))
          (Thread/sleep interval-msec)
          (recur)))
       conf-ref)))
