(ns health-samurai-back-end.root-router
  (:require
   [compojure.core :refer [defroutes context]]
   [compojure.route :as route]
   [health-samurai-back-end.components.patient.router :as patient-router]))

(defroutes root
  (context "/api" []
    patient-router/router)
  (route/not-found "not found"))
