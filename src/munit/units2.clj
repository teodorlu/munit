(ns munit.units2
  "API-et til bruk :)"
  (:refer-clojure :exclude [* / + -])
  (:require [munit.impl2 :refer [simplify invert div add negate sub]]))

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
