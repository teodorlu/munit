(ns teodorlu.munit.impl-test
  (:require [clojure.test :refer [deftest is]]
            [teodorlu.munit.impl :as impl]
            [teodorlu.munit.si :as si]
            [teodorlu.munit.units :as units]))

(deftest mult
  (is (= 1 1)))

(impl/coerce si/m)
