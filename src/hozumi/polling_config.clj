(ns hozumi.polling-config
  (:refer-clojure :exclude [set])
  (:require [clj-yaml.core :as yaml]
	    [clojure.java.io :as javaio]))

(def config (ref nil))
(def file (ref nil))
(def last-modified (ref 0))

(defn set
  ([f]
     (dosync
      (ref-set file (javaio/file f))
      (ref-set last-modified (.lastModified @file))
      (ref-set config (-> @file slurp yaml/parse-string)))))

(defn run
  ([interval-msec]
     (run @file interval-msec))
  ([f interval-msec]
     (run f interval-msec nil))
  ([f interval-msec callback]
     (dosync (ref-set file (javaio/file f)))
     (loop []
       (let [current-last-modified (.lastModified @file)]
	 (when (> current-last-modified @last-modified)
	   (dosync
	    (ref-set last-modified current-last-modified)
	    (ref-set config (-> @file slurp yaml/parse-string))
	    (when callback
	      (callback @config))))
	 (Thread/sleep interval-msec)
	 (recur)))))
