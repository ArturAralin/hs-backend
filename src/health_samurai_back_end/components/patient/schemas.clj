(ns health-samurai-back-end.components.patient.schemas
  (:require
   [clojure.spec.alpha :as s]
   [db.tools]))

;; ISO 8601 YYYY-MM-DD
(def date-patter #"^(20|19)?\d{2}-\d{2}-\d{2}$")

(s/def ::limit
  #(let [v (db.tools/try-int %)]
     (and (int? v) (> v 0))))

(s/def ::offset
  #(let [v (db.tools/try-int %)]
     (and (int? v) (>= v 0))))


;; possibly it must be part of model
;; begin
(s/def ::id (s/and int? #(> % 0)))
(s/def ::fio string?)
(s/def ::gender string?)
(s/def ::birthday
  (s/or
   :date (s/and
    string?
    #(some? (re-find date-patter %)))
   :null nil?))
(s/def ::address string?)
(s/def ::oms_policy (s/and
                     string?
                     #(= (count %) 16)))
;; end

(s/def ::patient-creation
  (s/keys :req-un [::fio
                   ::gender
                   ::birthday
                   ::oms_policy]
          :opt-un [::address]))

(s/def ::patient-updating
  (s/and
   (s/keys :req-un [::id]
           :opt-un [::fio
                    ::gender
                    ::birthday
                    ::oms_policy
                    ::address])
    #(> (count %) 1)))

(s/def ::patients (s/coll-of #(s/valid? ::patient-creation %) :min-count 1))
(s/def ::update_patients (s/coll-of #(s/valid? ::patient-updating %) :min-count 1))


(s/def ::ids (s/coll-of #(s/valid? ::id %)))

(def get-patients
  {:params (s/keys :req-un [::limit ::offset])})

(def create-patient
  {:body (s/keys :req-un [::patients])})

(def update-patient
  {:body (s/keys :req-un [::update_patients])})

(def delete-patients
  {:body (s/keys :req-un [::ids])})
