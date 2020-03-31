(ns health-samurai-back-end.components.patient.router
  (:require
   [compojure.core :refer [routes GET POST PUT DELETE]]
   [health-samurai-back-end.components.patient.controller :as ctrl]))

(def router
  (routes
   (GET "/patient" req (ctrl/get-patient req))
   (POST "/patient" req (ctrl/create-patient req))
   (PUT "/patient" req (ctrl/update-patient req))
   (DELETE "/patient" req (ctrl/delete-patient req))))
