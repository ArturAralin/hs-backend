(ns health-samurai-back-end.core
  (:require
   [org.httpkit.server :refer [run-server]]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [health-samurai-back-end.root-router :as router])
  (:gen-class))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn wrap-json-response-if-accept [handler]
  (fn [req]
    (let [handler (let [accept (-> req :headers (get "accept"))]
              (if (= accept "application/json")
                (wrap-json-response handler)
                handler))]
      (handler req))))

(defn wrap-app [app]
  (-> app
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response-if-accept))

(defn -main []
  (reset! server (run-server (wrap-app #'router/root) {:port 8080})))

(defn reload-all []
  (stop-server)
  (use 'health-samurai-back-end.core :reload-all)
  (-main))
