(ns munit.units
  "Tools for making unit systems.

  Units give you arithmethic on quanity. A quanitity has a size and a unit. The
  SI unit system[1] is one such system, but you're free to create others.

  [1]: https://en.wikipedia.org/wiki/International_System_of_Units

  Quantities can be plain numbers, or base units.
    4 (unitless)
    si/m

  You can create new quantities by using the provided arithmetic operators (*,
  /, +, -) on other quantities.
    (u/* 4 si/m)
    (def km (* 1000 si/m)
    (def mm (/ si/m 1000)

  Units are represented as numbers, vectors or maps. Numbers are unitless.
    4 ; four
  Vectors imply multipliaction of terms.
    [4 si/m] ; four meters
  Maps are base unit exponents.
    {si/m 2} ; square meters
    [4 {si/m 2}] ; four square meters

  You can contruct any derived unit yourself:
    (def km (u/* 1000 si/m))"
  (:refer-clojure :exclude [* / + -])
  (:require [munit.impl :as impl
             :refer [coerce simplify
                     invert negate
                     mul div add sub]]))

(defn define-system [{:keys [bases]}]
  {:bases (into (sorted-set) bases)})

(defn base [system-var unit-sym]
  (when-not (and (var? system-var)
                 (map? (deref system-var)))
    (throw (ex-info "system-var must be a var poting to a unit system"
                    {:system-var system-var})))
  (when-not (symbol? unit-sym)
    (throw (ex-info "unit-sym must be a symbol"
                    {:unit-sym unit-sym})))
  (when-not (contains? (:bases (deref system-var))
                       unit-sym)
    (throw (ex-info "The bases of the unit system must contain the unit"
                    {:system-var system-var
                     :unit-sym unit-sym})))
  (impl/->BaseUnit system-var unit-sym))

(defn *
  ([] 1)
  ([x] x)
  ([x y] (simplify (mul (coerce x) (coerce y))))
  ([x y & args]
   (simplify (reduce mul
                     (mul (coerce x) (coerce y))
                     (map coerce args)))))

(defn /
  ([x] (-> x coerce invert simplify))
  ([x y] (simplify (div (coerce x)
                        (coerce y))))
  ([x y & args]
   (simplify (reduce div
                     (div (coerce x) (coerce y))
                     (map coerce args)))))

(defn +
  ([] 0)
  ([x] x)
  ([x y] (simplify (add (coerce x) (coerce y))))
  ([x y & args]
   (simplify (reduce add
                     (add (coerce x) (coerce y))
                     (map coerce args)))))

(defn -
  ([x] (-> x coerce negate simplify))
  ([x y] (simplify (sub (coerce x) (coerce y))))
  ([x y & args]
   (simplify (reduce sub
                     (sub (coerce x) (coerce y))
                     (map coerce args)))))
