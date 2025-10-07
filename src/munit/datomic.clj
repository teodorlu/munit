(ns munit.datomic
  "Datomic storage for munit

  1. You get namespaced attributes with meaning, not opaque strings.
     That means you can do things like \"find grams in my database\".

  2. While munit doesn't care whether you use whole numbers or decimals, your
     Datomic schema does. You need to pick a coercion strategy for magnitudes and
     base unit exponents."
  (:refer-clojure :exclude [*])
  (:require [munit.units :refer [* magnitude unit]]))

(comment
  ;; A starting point for your schema.

  ;; Quantities are entities
  {:db/ident :munit/quantity
   :db/valueType :db.type/ref
   :db/cardinality :db.cardinality/one
   :db/isComponent true}

  ;; Units are entities
  {:db/ident :munit/unit
   :db/valueType :db.type/ref
   :db/cardinality :db.cardinality/one
   :db/isComponent true}

  ;; ... and base units are symbols.
  {:db/ident :munit/base-unit
   :db/valueType :db.type/symbol
   :db/cardinality :db.cardinality/one
   :db/isComponent true}

  ;; You have to choose how to represent numbers in your database yourself.
  ;; - Doubles can represent all longs, but then you coerce whole numbers to doubles
  ;; - Longs cannot represent fractions.
  {:db/ident :munit/quantity
   ;; :db/valueType is :db.type/double or :db.type/long
   :db/cardinality :db.cardinality/one
   :db/isComponent true}

  {:db/ident :munit/base-unit
   ;; :db/valueType is :db.type/double or :db.type/long
   :db/cardinality :db.cardinality/one
   :db/isComponent true}

  )

(defn from-entity [e]
  (* (:munit/magnitude e)
     (into {}
           (map (fn [base+exponent]
                  [(:munit/base-unit base+exponent)
                   (:munit/exponent base+exponent)]))
           (:munit/unit e))))

(defn to-entity [x coerce-magnitude coerce-exponent]
  {:munit/magnitude (coerce-magnitude (magnitude x))
   :munit/unit (into #{}
                     (map (fn [[base-unit exponent]]
                            {:munit/base-unit base-unit
                             :munit/exponent (coerce-exponent exponent)}))
                     (unit x))}
  )
