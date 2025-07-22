(ns munit.impl
  "Boilerplate for working with units. Do not use directly."
  (:require munit.runtime))

(set! *warn-on-reflection* true)

(defrecord BaseUnit [system sym])

(defmethod print-method BaseUnit [^BaseUnit base-unit ^java.io.Writer w]
  (.write w (pr-str (.sym base-unit))))

(defrecord Quantity [magnitude exponents])

;; Reload units after redefining records.
(when munit.runtime/dev?
  (require 'munit.si :reload))

(defn coerce [x]
  (cond (instance? BaseUnit x)
        (Quantity. 1 {x 1})

        (number? x)
        (Quantity. x {})

        (map? x)
        (Quantity. 1 x)

        ))

(defn simplify [^Quantity q]
  (if (every? zero? (vals (.exponents q)))
    (.magnitude q)
    (->> [[(.magnitude q)]
          (->> (.exponents q)
               (filter (comp #{1} second))
               (map first))
          (some->> (.exponents q)
                   (remove (comp #{0 1} second))
                   (into {})
                   not-empty
                   vector)]
         (into [] cat))))

(defn mult [x y])
(defn div [x y])
(defn add [x y])
(defn sub [x y])
