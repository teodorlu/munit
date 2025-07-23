(ns munit.si
  "Unit system for SI

  See:
  - https://en.wikipedia.org/wiki/SI_base_unit
  - https://en.wikipedia.org/wiki/SI_derived_unit"
  (:require [munit.units :as units]))

(def si
  (units/define-system
    {:bases '[s m kg A K mol cd]}))

(def s (units/base #'si 's))
(def m (units/base #'si 'm))
(def kg (units/base #'si 'kg))
(def A (units/base #'si 'A))
(def K (units/base #'si 'K))
(def mol (units/base #'si 'mol))
(def cd (units/base #'si 'cd))

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
