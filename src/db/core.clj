(ns db.core
  (:require
   [clojure.java.jdbc :as jdbc]
   [environ.core :refer [env]]
   [honeysql.core :as sql]))

(def db-config {:dbtype (:database-type env)
            :dbname (:database-name env)
            :host (:database-host env)
            :user (:database-username env)
            :password (:database-password env)
            :port (:database-port env)})

(defn raw! [query]
  (io! (jdbc/query db-config query)))

(defn q! [query]
  (raw! (sql/format query)))
