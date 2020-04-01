(ns tools.routing
  (:require
   [ring.util.response :refer [bad-request]]
   [clojure.spec.alpha :as spec]))

;; return only existing errors
(defn- get-errors [validation-result]
  (->> validation-result
       vals
       (map #(second %))
       (filter not-empty)))

;; returns hash-map with validation results
(defn- get-vals [validation-result]
  (->> validation-result
       (map #(hash-map (first %) (second (second %))))
       (reduce into {})))

(defn- valid? [validation-result]
  (->> validation-result
       vals
       (every? #(= :valid (first %)))))

;; returns validation problems from explained data
(defn- get-problems [schema data]
  (let [problems (into {} (:clojure.spec.alpha/problems (spec/explain-data schema data)))
        path (:in problems)
        val (:val problems)]
    {:path path
     :value val}))

;; applies validation to data
(defn- apply-validate [schema data]
  (if (some? schema)
    (let [validation-result (spec/conform schema data)]
      (if (= validation-result :clojure.spec.alpha/invalid)
        [:invalid (get-problems schema data)]
        [:valid validation-result]))
    [:valid {}]))

;; applies validation by schema
(defn- validate [schemas req]
  ;; TODO: check if data exists but schema not
  (->> [:body :params]
       (map #(hash-map % (apply-validate (% schemas) (% req))))
       (reduce into {})))

;; validate request data via schema
(defn with-validation [handler schemas]
  (fn [req]
    (let [validation-result (validate schemas req)]
      (if (valid? validation-result)
        (handler (merge req (get-vals validation-result)))
        (bad-request {:status :bad-request
                      :details (get-errors validation-result)})))))
