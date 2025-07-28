;; # μnit examples, via [Broch's README](https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/README.md)

(ns from-broch-readme
  (:refer-clojure :exclude [* / + -])
  (:require
   [broch.core :as b]
   [munit.convert :refer [feet]]
   [munit.si :refer [m s]]
   [munit.units :refer [* measure-in]]))

;; ## Broch
(b/meters 10)
(b/feet (b/meters 10))

;; ## μnit

(* 10 m)

;; Meters can be measured in feed
(measure-in (* 10 m) feet)

;; ... but not in seconds.
(defmacro expect-ex [& body]
  `(try ~@body (catch Exception e# [(ex-message e#) (ex-data e#)])))

(expect-ex (measure-in (* 10 m) s))

;; ## Units as data

;; Under the hood, numbers with units are represented with the Quantity type.
(require 'munit.impl)
(munit.impl/coerce [1 m])
(type (munit.impl/coerce [1 m]))

;; But you generally don't see that, as operations simplify before returning.
(-> [1 m] munit.impl/coerce munit.impl/simplify)

;; The goal?
;; That you never have to see raw Quanities.
;; Instead, you'll see data.
;; In fact, you've already seen all the data formats.

(require '[munit.prefix :refer [k]])

[[42 "Unitless number"]
 [m "Base unit"]
 [{m 2} "Exponents of base units"]
 [[k m] "Factors"]
 [[144 k m {s -1}] "... and a combination."]
 ]

;; Some shenaningans is done to print base units as symbols.
;; Because they aren't symbols.
;; They keep a reference to their unit system, letting us ask questions as
;;  "do these two units belong to the same unit system?"
;;  or "give me every base unit in this unit's unit system.

;; `munit.units`, the prime namespace, is small.
;; The goal is to let you build other things on top.
(-> 'munit.units ns-publics keys sort)
