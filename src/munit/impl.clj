(ns munit.impl
  "Unit arithmetic implementation details, do not use directly."
  (:refer-clojure :exclude [+ - * /]))

(defn magnitude [x]
  (cond (number? x)
        x

        (vector? x)
        (reduce clojure.core/* (map magnitude x))

        ;; Otherwise, it's a unit, magnitude is 1.
        :else
        1))

(defn remove-vals [m pred?]
  (reduce (fn [the-map [k v]]
            (cond-> the-map
              (pred? v)
              (dissoc k)))
          m
          m))

(defn mul-units [u1 u2]
  (merge-with clojure.core/+ u1 u2))

(defn unit [x]
  (-> (cond (number? x)
            {}

            (vector? x)
            (reduce mul-units {} (map unit x))

            (map? x)
            x

            ;; Technically, we could allow any comparable data as the base unit.
            ;; We only need "keyable in map". Symbols and keywords were
            ;; considered as options. For keywords, :si/m woule be kind of nice,
            ;; no?
            ;;
            ;; Ultimately, the writing of [[munit.datomic]] informed this
            ;; decision:
            ;; - symbols print more naturally than keywords
            ;; - and by picking *only symbols*, we can write a Datomic schema without ambiguity.
            ;;
            ;; In conclusion: base units are symbols.
            (symbol? x)
            {x 1}

            :else
            (throw (ex-info "Cannot infer unit from number" {:number x})))
      (remove-vals zero?)))

(defn simplify [x]
  (cond
    ;; Enhetsløse tall forenkler til kun tallet
    (every? zero? (vals (unit x)))
    (magnitude x)

    ;; Tall med størrelse 1 forenkler til kun enheten
    (and (= 1 (magnitude x))
         (every? #(not= 1 %) (vals (unit x))))
    (unit x)

    ;; for øvrige tall gir vi en vektor av faktorer.
    :else
    (->> [[(magnitude x)]
          (->> (unit x)
               (filter (comp #{1} second))
               (map first))
          (some->> (unit x)
                   (remove (comp #{0 1} second))
                   (into {})
                   not-empty
                   vector)]
         (into [] cat))))

(defn mul [x y]
  (simplify [x y]))

(defn map-vals [m f]
  (reduce (fn [m' [k v]]
            (assoc m' k (f v)))
          {}
          m))

(def negate-vals #(map-vals % clojure.core/-))

(defn invert [x]
  (simplify [(clojure.core// (magnitude x))
             (negate-vals (unit x))]))

(defn div [x y]
  (simplify [(clojure.core// (magnitude x)
                             (magnitude y))
             (merge-with clojure.core/+
                         (unit x)
                         (negate-vals (unit y)))]))

(defn same-unit? [x y]
  (= (unit x) (unit y)))

(defn add [x y]
  (when-not (same-unit? x y)
    (throw (ex-info "Cannot add quantities of different units"
                    {:x x :y y})))
  (simplify
   [(clojure.core/+ (magnitude x) (magnitude y))
    (unit x)]))

(defn negate [x]
  (simplify [(clojure.core/- (magnitude x))
             (unit x)]))

(defn sub [x y]
  (when-not (same-unit? x y)
    (throw (ex-info "Cannot subtract quantities of different units"
                    {:x x :y y})))
  (simplify
   [(clojure.core/- (magnitude x) (magnitude y))
    (unit x)]))
