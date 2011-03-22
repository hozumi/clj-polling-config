(ns hozumi.clj-polling-config.test.core
  (:use [hozumi.polling-config] :reload)
  (:use [clojure.test]))

(deftest test-polling
  (let [c (run-background "test/config.clj" 500)]
    (Thread/sleep 1000)
    (is (= {:Hello "World"} @c))))
