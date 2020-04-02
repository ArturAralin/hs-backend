(ns health-samurai-back-end.components.patient.router
  (:require
   [compojure.core :refer [routes GET POST PUT DELETE]]
   [tools.routing :refer [with-validation]]
   [health-samurai-back-end.components.patient.controller :as ctrl]
   [health-samurai-back-end.components.patient.schemas :as schema]))

(def router
  (routes
   (GET "/patients" req
     ((with-validation ctrl/get-patient schema/get-patients) req))

   (POST "/patients" req
     ((with-validation ctrl/create-patient schema/create-patient) req))

   (PUT "/patients" req
     ((with-validation ctrl/update-patient schema/update-patient) req))

   (DELETE "/patients" req
     ((with-validation ctrl/delete-patient schema/delete-patients) req))))
