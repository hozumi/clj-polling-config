(ns hozumi.polling-config
  (:require [clj-yaml.core :as yaml]
	    [clojure.java.io :as javaio]))

(def config (ref nil))
(def last-modified (ref 0))

(defn run [file interval-msec]
  (loop [file (javaio/file file)]
    (let [current-last-modified (.lastModified file)]
      (when (> current-last-modified @last-modified)
	(dosync
	 (ref-set last-modified current-last-modified)
	 (ref-set config (-> file slurp yaml/parse-string))))
      (Thread/sleep interval-msec)
      (recur file))))
