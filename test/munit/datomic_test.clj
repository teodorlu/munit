(ns munit.datomic-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [munit.datomic]
   [munit.si]))

;; TODO property-test
;;
;; Equal to original
;; munit -> datomic entity (map) -> munit
;;
;; Can be stored without crash
;; munit -> datomic entity (map) -> transact

(def m 'm)

(deftest to-entity
  (is (= (munit.datomic/to-entity [5 m] long long)
         {:munit/magnitude 5
          :munit/unit #{{:munit/base-unit 'm
                         :munit/exponent 1}}}))

  (is (= (munit.datomic/to-entity [5 {m 2}] long long)
         {:munit/magnitude 5
          :munit/unit #{{:munit/base-unit 'm
                         :munit/exponent 2}}}))

  (is (= (munit.datomic/to-entity [5 {m 2}] double long)
         {:munit/magnitude 5.0
          :munit/unit #{{:munit/base-unit 'm
                         :munit/exponent 2}}}))

  (is (= (munit.datomic/to-entity [5 {m 2}] long double)
         {:munit/magnitude 5
          :munit/unit #{{:munit/base-unit 'm
                         :munit/exponent 2.0}}}))

  )

(deftest from-entity
  (is (= (munit.datomic/from-entity
          {:munit/magnitude 5
           :munit/unit #{{:munit/base-unit 'm
                          :munit/exponent 1}}})
         [5 m]))

  (testing "decimal numbers will carry back into munit"
    (is (= (munit.datomic/from-entity
            {:munit/magnitude 5.0
             :munit/unit #{{:munit/base-unit 'm
                            :munit/exponent 2.0}}})
           [5.0 {m 2.0}])))

  )

(comment
  (set! *print-namespace-maps* false)

  (require '[munit.units :refer [unit]])
  (unit [5 m])

  (munit.datomic/to-entity [5.0 m] double double)
  ;; => {:munit/magnitude 5.0, :munit/unit #{{:munit/base-unit m, :munit/exponent 1.0}}}
  (munit.datomic/from-entity {:munit/magnitude 5.0, :munit/unit #{{:munit/base-unit m, :munit/exponent 1.0}}})
  ;; => [5.0 {m 1.0}]
  )
