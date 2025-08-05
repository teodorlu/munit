(ns munit.si
  "Unit system for SI

  See:
  - https://en.wikipedia.org/wiki/SI_base_unit
  - https://en.wikipedia.org/wiki/SI_derived_unit"
  (:require [munit.units :as units]))

(def s 's)
(def m 'm)
(def kg 'kg)
(def A 'A)
(def K 'K)
(def mol 'mol)
(def cd 'cd)

(comment
  ;; SI derived units
  (def Hz)
  (def rad)
  (def sr)
  (def N)
  (def Pa)
  (def J)
  (def W)
  (def C)
  (def V)
  (def F)
  (def Ω)
  (def S)
  (def Wb)
  (def T)
  (def H)
  (def °C)
  (def lm)
  (def lx)
  (def Bq)
  (def Gy)
  (def Sv)
  (def kat)

  ,)
