(ns munit.impl-test
  (:require [clojure.test :refer [deftest is testing]]
            [munit.impl :as i2]
            [munit.si :as si]))

(deftest simplify-test
  (testing "zero"
    (is (= 0 (i2/simplify 0))))
  (testing "to number"
    (is (= 42
           (i2/simplify 42)))
    (is (= [1 'm]
           (i2/simplify [1 {'m 1}]))))
  (testing "to map"
    (is (= {'si/s -1}
           (i2/simplify [1 {'si/s -1}]))))
  (testing "to map, with a custom type for units"
    (is (= {si/s -1}
           (i2/simplify [1 {si/s -1}]))))
  (testing "to vector containing map"
    (is (= [4 {'si/m 2}]
           (i2/simplify [4 {'si/m 2}]))))
  (testing "removes zero exponents"
    (is (= {'m 2}
           (i2/simplify {'m 2 's 0})))))

(deftest mult-test
  (testing "2*3"
    (is (= 6
           (i2/mul 2 3))))
  (testing "2m*3m"
    (is (= [6 {'si/m 2}]
           (i2/mul [2 'si/m] [3 'si/m]))))
  (testing "3 m/s"
    (is (= [3 'si/m {'si/s -1}]
           (i2/mul [3 'si/m] {'si/s -1})))))

(deftest invert-test
  (testing "2^-1"
    (is (= 1/2
           (i2/invert 2))))

  (testing "s^-1"
    (is (= '{si/s -1}
           (i2/invert 'si/s)))))

(deftest div-test
  (testing "6/2"
    (is (= 3
           (i2/div 6 2))))
  (testing "3 m/s"
    (is (= '[3 si/m {si/s -1}]
           (i2/div '[3 si/m] 'si/s)))))

(deftest same-unit?-test
  (is (i2/same-unit? 'si/m 'si/m))
  (is (i2/same-unit? 'si/m
                     (i2/mul 42 'si/m)))
  (is (not (i2/same-unit? 'si/m
                          (i2/mul 'si/m 'si/m)))))

(deftest add-test
  (is (= 3
         (i2/add 1 2)))
  (is (= '[3 si/m]
         (i2/add '[1 si/m] '[2 si/m]))))

(deftest negate-test
  (is (= -4
         (i2/negate 4))))


(deftest sub-test
  (is (= 1
         (i2/sub 3 2)))
  (is (= '[1 si/m]
         (i2/sub '[3 si/m] '[2 si/m]))))
