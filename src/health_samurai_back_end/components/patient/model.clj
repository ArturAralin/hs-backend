(ns health-samurai-back-end.components.patient.model
  (:require
   [honeysql.helpers :as q]
   [honeysql.core :as sql]
   [honeysql-postgres.helpers :as pg]
   [db.core :as db]
   [db.tools]))

(defrecord Patient
           [id
            fio
            gender
            birthday
            address
            oms_policy
            created_at
            updated_at])

(defn PatientRecord [patient]
  (let [birthday (when (contains? patient :birthday)
                   (db.tools/parse-date (:birthday patient)))]
    (map->Patient (merge patient {:birthday birthday}))))

;; Returns all patients
(defn get-all! [{limit :limit
                offset :offset}]
  (db/q!
   (->
    (q/select :*)
    (q/from :patients)
    (q/limit limit)
    (q/offset offset)
    (q/order-by [:id :desc]))))

(defn create-patients! [values]
  (let [patient-columns [:fio :gender :birthday :address :oms_policy]
        values (map #(select-keys % patient-columns) values)]
    (db/q!
     (->
      (q/insert-into :patients)
      (q/values values)
      (pg/returning :*)))))

(defn update-patient! [patient]
  (db/q!
   (let [id (:id patient)
         values (merge
                 (dissoc patient :id)
                 {:updated_at (sql/call :now)})]
     (->
      (q/update :patients)
      (q/sset values)
      (q/where [:= :id id])))))

(defn delete-patients-by-id! [ids]
  (db/q!
   (-> (q/delete-from :patients)
       (q/where [:in :id ids]))))
