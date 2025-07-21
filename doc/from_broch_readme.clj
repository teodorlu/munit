;; # μnit examples, via [Broch's README](https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/README.md)
;;
;; Intended to be viewed with Clerk.

(ns from-broch-readme
  (:refer-clojure :exclude [* / + -])
  (:require
   [nextjournal.clerk :as clerk]
   [teodorlu.munit.convert :as convert]
   [teodorlu.munit.si :refer [m]]
   [teodorlu.munit.units :refer [*]]))

;; We exclude Clojure's artithmetic operators, since we're using our own.

(clerk/caption
 "with Broch"
 (clerk/code '(b/meters 10)))

(* 1 1)

;; does not yet work:
;; (* 10 m)
;;
;; conclusion: I need a canonical Quantity type, otherwise I'm stuck in an
;; n*m-exposion of representations.
;;
;; canonicalize: anything -> Quantity
;;   (defrecord Quantity [magnitude exponents])
;;     exponents is an empty map for unitless numbers.
;;
;; simplify: anything -> number, vector or map
