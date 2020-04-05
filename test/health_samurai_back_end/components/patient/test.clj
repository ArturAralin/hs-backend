(ns health-samurai-back-end.components.patient.test
  (:require
   [clojure.test :refer [deftest testing is]]
   [ring.mock.request :as mock]
   [cheshire.core :refer [parse-string]]
   [health-samurai-back-end.core :as core]))

(defn try-json-parse [v]
  (try
    (parse-string v)
    (catch Exception _ (identity v))))

(defn request [method path]
  (let [res (core/app (mock/request method path))
        body (try-json-parse (:body res))]
    (merge res {:body body})))


(:body (request :get "/api/patients?limit=10&offset=0"))

(deftest patient-controller
  (testing "get-patients"
    (is (let [res (request :get "/api/patients?limit=10&offset=0")
              body (:body res)
              status (get body "status")]
          (= "ok" status)))

    (is (let [res (request :get "/api/patients?limit=aa&offset=0")
              body (:body res)
              status (get body "status")]
          (= "bad-request" status)))))
