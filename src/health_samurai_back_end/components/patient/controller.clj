(ns health-samurai-back-end.components.patient.controller
  (:require [ring.util.response :refer [response]]))

(defn get-patient [req]
  (response {:ok 1}))

(defn create-patient [req]
  (println (:body req))
  (response {:ok 2}))

(defn update-patient [req]
  (response {:ok 2}))

(defn delete-patient [req]
  (response {:ok 2}))