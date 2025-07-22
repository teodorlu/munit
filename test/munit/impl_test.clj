(ns munit.impl-test
  (:require [clojure.test :refer [deftest is testing]]
            [munit.impl :as impl :refer [simplify coerce ->Quantity]]
            [munit.si :as si]
            [munit.units :as units]))

(deftest simplify-test
  (testing "to number"
    (is (= 42
           (simplify (->Quantity 42 {})))))

  (testing "to vector of base units"
    (is (= [1 si/m]
           (simplify (->Quantity 1 {si/m 1})))))

  (testing "to vector containing map"
    (is (= [1 {si/m 2}]
           (simplify (->Quantity 1 {si/m 2})))))

  (testing "to vector of base units, then map"
    (is (= [9.81 si/m {si/s -2}]
           (simplify (->Quantity 9.81 {si/m 1 si/s -2}))))))

(deftest coerce-test
  (testing "base unit"
    (is (= (->Quantity 1 {si/m 1})
           (coerce si/m))))

  (testing "number"
    (is (coerce 42)))

  ;; Wait with vector coercion - implement mult first.
  #_
  (testing "vector"
    (is (= (->Quantity 2 {si/m 2})
           (coerce [2 si/m]))))

  (testing "map"
    (is (= (->Quantity 1 {si/m 1})
           (coerce {si/m 1})))))
