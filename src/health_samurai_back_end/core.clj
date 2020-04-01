(ns health-samurai-back-end.core
  (:require
   [environ.core :refer [env]]
   [org.httpkit.server :refer [run-server]]
   [ring.middleware.reload :as reload]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [health-samurai-back-end.root-router :as router])
  (:gen-class))

(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn wrap-autoreload [app]
  (if (= (:environment env) "development")
    (reload/wrap-reload app)
    app))

(defn wrap-app [app]
  (-> app
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response
      wrap-keyword-params
      wrap-params
      wrap-autoreload))

(defn -main [& _]
  (reset! server
          (run-server
           (wrap-app #'router/root)
           {:port (Integer. (:app-port env))})))
