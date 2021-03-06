(ns health-samurai-back-end.core
  (:require
   [environ.core :refer [env]]
   [org.httpkit.server :refer [run-server]]
   [ring.middleware.reload :as reload]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.cors :refer [wrap-cors]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [health-samurai-back-end.root-router :as router]
   [db.core])
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
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods #{:get :put :post :delete :options})
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response
      wrap-keyword-params
      wrap-params
      wrap-autoreload))

(def app (wrap-app #'router/root))

(defn -main [& _]
  (println (str "ENV: " (:environment env)))
  (println (str "DB: " (db.core/try-connection!)))
  (let [port (Integer. (:app-port env))]
    (reset! server
            (run-server
             app
             {:port port}))
    (println (str "Server started on " port))))
