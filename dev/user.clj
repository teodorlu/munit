(ns user
  (:require [clojure.repl.deps :refer [sync-deps add-lib]]))

(comment
  (add-lib 'no.anteo/broch)
  (sync-deps)
  )
