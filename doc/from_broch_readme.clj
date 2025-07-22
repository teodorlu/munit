;; # μnit examples, via [Broch's README](https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/README.md)
;;
;; Intended to be viewed with Clerk.

(ns from-broch-readme
  {:nextjournal.clerk/toc true}
  (:refer-clojure :exclude [* / + -])
  (:require
   [munit.convert :as convert]
   [munit.si :refer [m]]
   [munit.units :refer [*]]
   [nextjournal.clerk :as clerk]))

;; We exclude Clojure's artithmetic operators, since we're using our own.

;; ## Ten meters

^{:nextjournal.clerk/visibility {:code :hide}}
(clerk/caption
 "with Broch"
 (clerk/code '(b/meters 10)))

;; To make this work, I need:
;; - multiplication - 10 * meters
;; - coercion - coerce 10 and BaseUnit to Quantity
;; - simplification - to present the result.

;; Requirements:
;; - coerce BaseUnit to Quanity
;; - coerce number to Quanitty
;; - Simplify Quantity to vector of multiplication parts
