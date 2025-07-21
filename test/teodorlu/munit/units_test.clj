(ns teodorlu.munit.units-test
  (:require [clojure.test :refer [deftest is]]
            [teodorlu.munit.si :refer [m]]
            [teodorlu.munit.units :refer [*]]))

(deftest ten-meters
  (is (* 10 m)))

(* 10 m)
