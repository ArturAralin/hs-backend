(defproject health-samurai-back-end "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [environ "1.1.0"]
                 [http-kit "2.3.0"]
                 [ring-cors "0.1.13"]
                 [ring/ring-json "0.5.0"]
                 [ring "1.7.0"]
                 [compojure "1.6.1"]
                 [honeysql "0.9.10"]
                 [migratus "1.2.0"]
                 [org.postgresql/postgresql "42.1.4"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [nilenso/honeysql-postgres "0.2.6"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot health-samurai-back-end.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :prod {:env {:environment "production"
                          :app-port "80"
                          :database-type "postgresql"}}
             :dev {:env {:environment "development"
                         :app-port "8080"
                         :database-type "postgresql"}}
             :dev-local {:env {:database-name "health_samurai_app"
                               :database-username "postgres"
                               :database-password "postgres"
                               :database-host "localhost"
                               :database-port "5444"
                               }}
             :test {:dependencies [[ring/ring-mock "0.4.0"]
                                   [cheshire "5.10.0"]]}})
