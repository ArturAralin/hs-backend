(ns db.core
  (:require
   [clojure.java.jdbc :as jdbc]
   [environ.core :refer [env]]
   [honeysql.core :as sql]))

(def db-config
  {:dbtype (:database-type env)
   :dbname (:database-name env)
   :host (:database-host env)
   :user (:database-username env)
   :password (:database-password env)
   :port (:database-port env)})

;; exec raw JDBC query
(defn raw! [query]
  (try
    (io! (jdbc/query db-config query))
    (catch org.postgresql.util.PSQLException e
      (let [msg (.getMessage e)]
        (if (= "Запрос не вернул результатов." msg)
          (do
            (println "DB ERROR")
            '())
          (throw e))))))

;; exec honeysql query
(defn q! [query]
  (raw! (sql/format query)))

(defn try-connection! []
  (= 2 (:r (first (raw! ["SELECT 1 + 1 as r"])))))
