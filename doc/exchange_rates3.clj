(ns exchange-rates3
  (:refer-clojure :exclude [* / + -])
  (:require
   [clojure.set :as set]
   [clojure.string :as str]
   [scicloj.tableplot.v1.plotly :as plotly]
   [tablecloth.api :as tc]))

(def rates (tc/dataset "doc/eurofxref-hist.zip"))
rates

;; EUR to NOK:
(plotly/layer-line rates {:=x "Date" :=y "NOK"})

(tc/column rates "NOK")

(first (tc/rows rates))

(first rates)

;; SEK to NOK:

(-> rates
    (tc// "NOK per SEK" ["NOK" "SEK"])
    (plotly/layer-line {:=x "Date" :=y "NOK per SEK"}))

;; working with unitless numbers requires a bit of mental juggling!
;; What if we had units instead?

(require '[munit.units :refer [* / + -]])

(def EUR 'EUR)
(def NOK 'NOK)
(def SEK 'SEK)

;; (don't define more than we actually need, for now)

;; (second note - we opt out of tablecloth's niceness to use our unit system.
;;  it's currently not possible to use Munit's unit-sensitive operators with tablecloth columns.)

(require 'tech.v3.dataset-api)

(defn getter [k] (fn [m] (get m k)))

(defn ratemap [rates-ds k unit]
  (->> rates-ds
       tech.v3.dataset-api/rows
       (map (juxt (comp str (getter "Date"))
                  (comp (partial * {unit 1 EUR -1})
                        (getter k))))
       (into (sorted-map))))


(def date->EUR-NOK-rate (ratemap rates "NOK" NOK))
(def date->EUR-SEK-rate (ratemap rates "SEK" SEK))

(def date->NOK-SEK-rate
  (->> (set/intersection (set (keys date->EUR-NOK-rate))
                         (set (keys date->EUR-SEK-rate)))
       (map (juxt identity
                  (fn [date]
                    (/ (date->EUR-NOK-rate date)
                       (date->EUR-SEK-rate date)))))
       (into (sorted-map))))

(->> (keys date->NOK-SEK-rate)
     sort
     (take 10))

(get date->NOK-SEK-rate "2000-01-01")
(->> date->NOK-SEK-rate
     (drop-while (fn [[date _]]
                   (str/starts-with? date "1999")))
     (take 40))


;; at last, let's sample some currencies.

(->> (range 2000 2025)
     (map #(str % "-02-01"))
     (map (juxt identity date->NOK-SEK-rate)))
