(ns db.migrations
  (:require
   [db.core :refer [db-config]]
   [migratus.core :as migratus]))

(def config {:store :database
             :migration-dir "migrations/"
             :init-script "init.sql"
             :init-in-transaction? true
             :migration-table-name "__migrations"
             :db db-config})

(defn init []
  (migratus/init config))
