;; # μnit examples, via [Broch's README](https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/README.md)

(ns from-broch-readme
  {:nextjournal.clerk/toc true}
  (:refer-clojure :exclude [* / + -])
  (:require
   [broch.core :as b]
   [munit.convert :as convert]
   [munit.si :refer [m]]
   [munit.units :refer [*]]
   [nextjournal.clerk :as clerk]))

;; ## Ten meters
;; ### with broch

(b/meters 10)

;; ### with munit

(* 10 m)
