(ns health-samurai-back-end.components.patient.controller
  (:require
   [ring.util.response :refer [response]]
   [db.tools :refer [nil-to-null-keyword parse-date-in]]
   [health-samurai-back-end.components.patient.model :as PatientModel]))

(defn get-patient [req]
  (let [params (reduce
                (fn [x k] (update-in x [k] #(Integer. %)))
                (get-in req [:params])
                [:limit :offset])]
    (response
     {:status :ok
      :total (PatientModel/count-patients!)
      :response (PatientModel/get-all! params)})))

(defn create-patient [req]
  (response
   {:status :ok
    :response (->> (get-in req [:body :patients])
                   (map #(PatientModel/PatientRecord
                          (merge {:address ""} %)))
                   PatientModel/create-patients!)}))

(defn update-patient [req]
  (doall
   (->> (get-in req [:body :update_patients])
        (map #(-> % nil-to-null-keyword (parse-date-in [:birthday])))
        ;; here must be a tansaction
        (map PatientModel/update-patient!)))
  (response {:status :ok}))

(defn delete-patient [req]
  (doall
   (PatientModel/delete-patients-by-id!
    (->> (get-in req [:body :ids])
         (map #(Integer. %)))))
  (response {:status :ok}))
