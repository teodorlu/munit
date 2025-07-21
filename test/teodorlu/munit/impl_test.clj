(ns teodorlu.munit.impl-test
  (:require [clojure.test :refer [deftest is]]
            [teodorlu.munit.impl :as impl]))

(deftest mult
  (is (= 2 (impl/mult 1 2))))
