(ns tools.routing
  (:require
   [ring.util.response :refer [bad-request]]
   [struct.core :as v]))

;; filter undefined validation schemas
(defn- filter-empty-pairs [pairs]
  (filter #(some? (first %)) pairs))

;; valdate pairs with struct
(defn- validate [[schema data]]
  (v/validate data schema))

;; keeps only validation results
(defn- get-errors [pairs]
  (keep-indexed #(when (even? %1) %2) (flatten pairs)))

;; checks for not invalid results existig
(defn- valid? [pairs]
  (empty? (get-errors pairs)))

;; wrap handler with a validation
(defn with-validation [handler schemas]
  (fn [req]
    (let [pairs (filter-empty-pairs
                 [(map :body [schemas req])
                  (map :params [schemas req])
                  ;;  this is path parms
                  ;; (map :params [schemas req])
                  ])
          validation-result (map validate pairs)]
      (if (valid? validation-result)
        (handler req)
        (bad-request {:status :bad-request
                      :reasons (get-errors validation-result)})))))