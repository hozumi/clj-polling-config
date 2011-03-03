(ns hozumi.polling-config
  (:require [clojure.java.io :as javaio]))

(defn run-background
  ([file interval-msec]
     (run-background file interval-msec nil))
  ([file interval-msec callback]
     (let [file (javaio/file file)
           last-modified (ref 0)
           config (ref nil)]
       (future
        (loop []
          (let [current-last-modified (.lastModified file)]
            (when (> current-last-modified @last-modified)
              (dosync
               (ref-set last-modified current-last-modified)
               (ref-set config (-> file slurp read-string)))
              (when callback
                (callback @config))))
          (Thread/sleep interval-msec)
          (recur)))
       config)))
