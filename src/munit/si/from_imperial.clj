(ns munit.si.from-imperial
  (:refer-clojure :exclude [+ - / *])
  (:require [munit.si :refer [m]]
            [munit.units :refer [* /]]))

;; Consider cutting imperial from "munit core".
;;
;; We want to draw the line somewhere. And I'd rather support the full Frink
;; treasure trove via Frink data structures than copy-paste certain non SI units
;; by hand.

;; Thank you, Anteo!
;; https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/src/broch/core.cljc#L227
(def yard (* 0.9144 m))
(def mile (* 1760 yard))
(def feet (/ yard 3))
(def inch (/ feet 12))


(comment
  (require 'munit.si.prefix)
  (munit.units/measure-in inch [munit.si.prefix/m m])
  )
