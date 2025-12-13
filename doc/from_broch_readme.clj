;; # μnit examples, via [Broch's README](https://github.com/anteoas/broch/blob/66ead26e041c35907c59aea4e4237553ece47aaf/README.md)

(ns from-broch-readme
  (:refer-clojure :exclude [* / + -])
  (:require
   [broch.core :as b]
   [munit.si :refer [m s]]
   [munit.units :refer [* / measure-in]]))

(comment
  ((requiring-resolve 'clojure.repl.deps/add-lib) 'no.anteo/broch)
  )

;; ## Define imperial
(do
  (def yard (* 0.9144 m))
  (def mile (* 1760 yard))
  (def feet (/ yard 3))
  (def inch (/ feet 12)))

;; ## From imperial with Broch
(b/meters 10)
(b/feet (b/meters 10))

;; ## From imperial with Munit

(* 10 m)

(b/feet (b/meters 10))

;; Meters can be measured in feed

(measure-in (* 10 m) feet)

;; ... but not in seconds.
(defmacro expect-ex [& body]
  `(try ~@body (catch Exception e# [(ex-message e#) (ex-data e#)])))

(expect-ex (measure-in (* 10 m) s))
