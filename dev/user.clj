(ns user
  (:require munit.runtime))

(alter-var-root #'munit.runtime/dev? (constantly true))
