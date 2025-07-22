(ns munit.units-test
  (:require [clojure.test :refer [deftest is]]
            [munit.si :refer [m]]
            [munit.units :refer [*]]))

(deftest ten-meters
  (is true))
