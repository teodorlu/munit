(ns munit.impl-test
  (:require [clojure.test :refer [deftest is testing]]
            [munit.impl :as impl :refer [->Quantity
                                         coerce simplify same-unit?
                                         zero mult
                                         div add sub]]
            [munit.si :as si]))

(deftest simplify-test
  (testing "zero"
    (is (= 0 (simplify zero))))
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
  (testing "vector"
    (is (= (->Quantity 2 {si/m 1})
           (coerce [2 si/m]))))
  (testing "map"
    (is (= (->Quantity 1 {si/m 1})
           (coerce {si/m 1}))))
  (testing "already a quantity"
    (is (= (->Quantity 1 {si/m 1})
           (coerce (->Quantity 1 {si/m 1}))))))

(defn coerce->f->simplify [f & args]
  (->> (map coerce args)
       (apply f)
       simplify))

(deftest mult-test
  (testing "2*3"
    (is (= 6
           (coerce->f->simplify mult 2 3))))
  (testing "2m*3m"
    (is (= [6 {si/m 2}]
           (coerce->f->simplify mult [2 si/m] [3 si/m]))))
  (testing "3 m/s"
    (is (= [3 si/m {si/s -1}]
           (coerce->f->simplify mult [3 si/m] {si/s -1})))))

(deftest same-unit?-test
  (is (same-unit? (coerce si/m) (coerce si/m)))
  (is (same-unit? (coerce si/m)
                  (mult (coerce 42) (coerce si/m))))
  (is (not (same-unit? (coerce si/m)
                       (mult (coerce si/m) (coerce si/m))))))

(deftest div-test
  (testing "6/2"
    (is (= 3
           (coerce->f->simplify div 6 2))))
  (testing "3 m/s"
    (is (= [3 si/m {si/s -1}]
           (coerce->f->simplify div [3 si/m] si/s)))))

(deftest add-test
  (is (= 3
         (coerce->f->simplify add 1 2)))
  (is (= [3 si/m]
         (coerce->f->simplify add [1 si/m] [2 si/m]))))

(deftest sub-test
  (is (= 1
         (coerce->f->simplify sub 3 2)))
  (is (= [1 si/m]
         (coerce->f->simplify sub [3 si/m] [2 si/m]))))
