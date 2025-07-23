(ns user
  (:require [clojure.repl.deps :refer [sync-deps add-lib]]
            munit.runtime))

(alter-var-root #'munit.runtime/dev? (constantly true))

(comment
  (add-lib 'no.anteo/broch)
  (sync-deps)
  )
