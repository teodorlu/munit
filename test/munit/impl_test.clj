(ns munit.impl-test
  (:require [clojure.test :refer [deftest is]]
            [munit.impl :as impl]
            [munit.si :as si]
            [munit.units :as units]))

(deftest mult
  (is (= 1 1)))

(impl/coerce si/m)
