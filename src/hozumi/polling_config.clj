(ns hozumi.polling-config
  (:require [clojure.java.io :as javaio]))

(defn run-background
  ([file interval-msec]
     (run-background file interval-msec nil))
  ([file interval-msec callback]
     (run-background file interval-msec callback (ref nil)))
  ([file interval-msec callback conf-ref]
     (let [file (javaio/file file)
           last-modified (ref 0)]
       (future
        (loop []
          (let [current-last-modified (.lastModified file)]
            (when (> current-last-modified @last-modified)
              (dosync
               (ref-set last-modified current-last-modified)
               (ref-set conf-ref (-> file slurp read-string)))
              (when callback
                (callback @conf-ref))))
          (Thread/sleep interval-msec)
          (recur)))
       conf-ref)))
