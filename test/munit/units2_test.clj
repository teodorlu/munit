(ns munit.units2-test
  (:refer-clojure :exclude [+ - / *])
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [munit.prefix :refer [k]]
            [munit.units2 :refer [* / + - measure-in]]))

(def m 'm)
(def s 's)

(deftest *-test
  (is (= 1 (*)))
  (is (= 4 (* 2 2)))
  (is (= [4 m] (* 4 m)))
  (is (= [4 {m 2}] (* 4 m m)))
  (is (= [9 m {s -1}] (* 9 m {s -1})))
  )

(deftest divide-test
  (is (= 1/3 (/ 3)))
  (is (= 2/3 (/ 2 3)))
  (is (= {s -1} (/ s)))
  (is (= [1 s] (-> s / /)))
  (is (= [1 m {s -1}] (/ m s)))
  (is (= {s -2} (/ 1 s s)))
  )

(defmacro expect-message [& body]
  `(::the-ex-message
    (try
      ~@body
      (catch Exception e#
        {::the-ex-message
         (ex-message e#)}))))

(deftest +-test
  (is (= 0 (+)))
  (is (= 7 (+ 7)))
  (is (= 2 (+ 1 1)))
  (is (= [2 m] (+ m m)))
  (is (str/includes? (expect-message (+ m s))
                     "different units"))
  (is (= [3 m] (+ m m m)))
  )


(deftest --test
  (is (= -1 (- 1)))
  (is (= [-2 m] (- [3 m] [5 m])))
  (is (str/includes? (expect-message (- [1 m] [5 s]))
                     "different units"))
  (is (= [8 m]
         (- [10 m] [1 m] [1 m])))
  )

(def km (* k m))

(deftest measure-in-test
  (is (= 1 (measure-in m m)))
  (is (= 1000 (measure-in km m)))
  (is (= 1/1000 (measure-in m km)))
  (is (str/includes? (expect-message (measure-in [5 km] s))
                     "Cannot convert"))
  )
