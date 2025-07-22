(ns munit.impl
  "Boilerplate for working with units. Do not use directly."
  (:require munit.runtime))

(defrecord BaseUnit [system sym])

(defmethod print-method BaseUnit [base-unit ^java.io.Writer w]
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

        ))

(defn simplify [q])

(defn mult [x y])
(defn div [x y])
(defn add [x y])
(defn sub [x y])
