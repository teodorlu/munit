(ns munit.impl-test
  (:require [clojure.test :refer [deftest is testing]]
            [munit.impl :as impl]
            [munit.si :as si]
            [munit.units :as units]))

(deftest coerce
  (testing "base unit"
    (is (impl/coerce si/m)))

  (testing "number"
    (is (impl/coerce 42))))
