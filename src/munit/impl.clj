(ns munit.impl
  "Boilerplate for working with units. Do not use directly."
  (:require clojure.pprint
            munit.runtime))

(set! *warn-on-reflection* true)

(defrecord BaseUnit [system sym])

(defmethod print-method BaseUnit [^BaseUnit base-unit ^java.io.Writer w]
  (.write w (pr-str (.sym base-unit))))

(defmethod clojure.pprint/simple-dispatch BaseUnit [^BaseUnit base-unit]
  (clojure.pprint/write-out (.sym base-unit)))

(defrecord Quantity [magnitude exponents])

;; Reload SI units after redefining records.
(when (and munit.runtime/dev?
           (contains? (loaded-libs) 'munit.si))
  (require 'munit.si :reload))

(def one (Quantity. 1 {}))
(def zero (Quantity. 0 {}))

(declare mul)

(defn coerce [x]
  (cond (instance? Quantity x)
        x

        (instance? BaseUnit x)
        (Quantity. 1 {x 1})

        (number? x)
        (Quantity. x {})

        (map? x)
        (Quantity. 1 x)

        (vector? x)
        (reduce mul one (map coerce x))

        :else
        (throw (ex-info "Cannot coerce value to quantity" {:value x}))))

(def remove-zero-exponents #(into {} (remove (comp clojure.core/zero? second) %)))

(defn canonicalize ^Quantity [^Quantity q]
  (Quantity. (.magnitude q)
             (remove-zero-exponents (.exponents q))))

(defn simplify [^Quantity q]
  (cond
    (every? zero? (vals (.exponents q)))
    (.magnitude q)

    (and (= 1 (.magnitude q))
         (every? #(not= 1 %) (vals (.exponents q))))
    (.exponents q)

    :else
    (->> [[(.magnitude q)]
          (->> (.exponents q)
               (filter (comp #{1} second))
               (map first))
          (some->> (.exponents q)
                   (remove (comp #{0 1} second))
                   (into {})
                   not-empty
                   vector)]
         (into [] cat))))

(defn mul [^Quantity x ^Quantity y]
  (Quantity. (* (.magnitude x) (.magnitude y))
             (merge-with + (.exponents x) (.exponents y))))

(defn negate-map-vals [m]
  (into {}
        (map (fn [[k v]] [k (- v)]))
        m))

(defn invert [^Quantity x]
  (Quantity. (/ (.magnitude x))
             (negate-map-vals (.exponents x))))

(defn div [^Quantity x ^Quantity y]
  (Quantity. (/ (.magnitude x) (.magnitude y))
             (merge-with + (.exponents x) (negate-map-vals (.exponents y)))))

(defn same-unit? [^Quantity x ^Quantity y]
  (= (.exponents (canonicalize x))
     (.exponents (canonicalize y))))

(defn add [^Quantity x ^Quantity y]
  (when-not (same-unit? x y)
    (throw (ex-info "Cannot add quantities of different units"
                    {:x x :y y})))
  (Quantity. (+ (.magnitude x) (.magnitude y))
             (.exponents x)))

(defn negate [^Quantity x]
  (Quantity. (- (.magnitude x)) (.exponents x)))

(defn sub [^Quantity x ^Quantity y]
  (when-not (same-unit? x y)
    (throw (ex-info "Cannot subtract quantities of different units"
                    {:x x :y y})))
  (Quantity. (- (.magnitude x) (.magnitude y))
             (.exponents x)))
