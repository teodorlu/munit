(ns munit.units
  "Arithmetic on numbers with unit

  Units give you arithmethic on quanity. A quanitity has a magnitude and a unit.
  The SI unit system[1] is one such system, but you're free to create others.

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
  Vectors imply multiplication of terms.
    [4 si/m] ; four meters
  Maps are base unit exponents.
    {si/m 2} ; square meters
    [4 {si/m 2}] ; four square meters

  You can contruct any derived unit yourself:
    (def km (u/* 1000 si/m))"
  (:refer-clojure :exclude [* / + -])
  (:require [munit.impl :refer [simplify invert div add negate sub]]))

(defn *
  ([] 1)
  ([x] x)
  ([x y] (simplify [x y]))
  ([x y & args]
   (simplify [x y (vec args)])))

(defn /
  ([x] (invert x))
  ([x y] (div x y))
  ([x y & args]
   (reduce div (div x y) args)))

(defn +
  ([] 0)
  ([x] x)
  ([x y] (add x y))
  ([x y & args]
   (reduce add (add x y) args)))

(defn -
  ([x] (negate x))
  ([x y] (sub x y))
  ([x y & args]
   (reduce sub (sub x y) args)))

(defn measure-in [x target-unit]
  (let [converted (/ x target-unit)]
    (when-not (number? converted)
      (throw (ex-info "Cannot convert to target unit"
                      {:quantity x
                       :target-unit target-unit
                       :leftover converted})))
    converted))
