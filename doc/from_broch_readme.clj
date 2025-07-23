;; # μnit examples, via [Broch's README](https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/README.md)

(ns from-broch-readme
  {:nextjournal.clerk/toc true}
  (:refer-clojure :exclude [* / + -])
  (:require
   [broch.core :as b]
   [munit.convert :refer [yard]]
   [munit.si :refer [m]]
   [munit.units :refer [* measure-in]]
   [nextjournal.clerk :as clerk]))

{::clerk/visibility {:code :hide}}

;; ## Broch
(clerk/example
 (b/meters 10)
 (b/feet (b/meters 10)))

;; ## μnit

(clerk/example
 (* 10 m)
 (measure-in (* 10 m) yard))
