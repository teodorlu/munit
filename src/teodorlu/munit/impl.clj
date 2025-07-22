(ns teodorlu.munit.impl
  "Boilerplate for working with units. Do not use directly."
  ;; (:refer-clojure :exclude [type])
  )

(ns-unmap *ns* 'mult)

(defrecord BaseUnit [system sym])

(defmethod print-method BaseUnit [base-unit ^java.io.Writer w]
  (.write w (pr-str (.sym base-unit))))

(defrecord Quantity [magnitude exponents])

(defn coerce [x]
  (cond (instance? BaseUnit x)
        (Quantity. 1 {x 1})))

(comment
  (instance? teodorlu.munit.impl.BaseUnit teodorlu.munit.si/m)

  (= BaseUnit (type teodorlu.munit.si/m))

  ;; SI needs to be reloaded every time records are changed.
  (require 'teodorlu.munit.si :reload)
  )

(defn simplify [q])

(defn mult [x y])

;; ;; Multiply
;; (defn mult-num-num [n n])
;; (defn mult-num-vec [n v])
;; (defn mult-num-map [n m])
;;
;; (defn mult-vec-num [v n])
;; (defn mult-vec-vec [v v])
;; (defn mult-vec-map [v m])
;;
;; (defn mult-map-num [v n])
;; (defn mult-map-vec [v v])
;; (defn mult-map-map [v m])
;;
;; ;; Divide
;; (defn div-num-num [n n])
;; (defn div-num-vec [n v])
;; (defn div-num-map [n m])
;;
;; (defn div-vec-num [v n])
;; (defn div-vec-vec [v v])
;; (defn div-vec-map [v m])
;;
;; (defn div-map-num [v n])
;; (defn div-map-vec [v v])
;; (defn div-map-map [v m])
;;
;; ;; Add
;; (defn add-num-num [n n])
;; (defn add-num-vec [n v])
;; (defn add-num-map [n m])
;;
;; (defn add-vec-num [v n])
;; (defn add-vec-vec [v v])
;; (defn add-vec-map [v m])
;;
;; (defn add-map-num [v n])
;; (defn add-map-vec [v v])
;; (defn add-map-map [v m])
;;
;; ;; Subtract
;; (defn sub-num-num [n n])
;; (defn sub-num-vec [n v])
;; (defn sub-num-map [n m])
;;
;; (defn sub-vec-num [v n])
;; (defn sub-vec-vec [v v])
;; (defn sub-vec-map [v m])
;;
;; (defn sub-map-num [v n])
;; (defn sub-map-vec [v v])
;; (defn sub-map-map [v m])
