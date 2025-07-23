(ns munit.convert
  (:refer-clojure :exclude [+ - / *])
  (:require [munit.si :refer [m]]
            [munit.units :refer [* /]]))

;; Thank you, Anteo!
;; https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/src/broch/core.cljc#L227
(def yard (* 0.9144 m))
(def mile (* 1760 yard))
(def feet (/ yard 3))
(def inch (/ feet 12))
