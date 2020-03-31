(defproject health-samurai-back-end "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [environ "1.1.0"]
                 [http-kit "2.3.0"]
                 [ring/ring-json "0.5.0"]
                 [compojure "1.6.1"]
                 [funcool/struct "1.3.0"]
                 [honeysql "0.9.10"]
                 [migratus "1.2.0"]
                 [org.postgresql/postgresql "42.1.4"]
                 [org.clojure/java.jdbc "0.7.11"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot health-samurai-back-end.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:env {:environment "development"
                         :database-type "postgresql"}}
             :dev-local {:env {
                               :database-name "health_samurai_app"
                               :database-username "postgres"
                               :database-password "postgres"
                               :database-host "localhost"
                               :database-port "5444"
                               }}})
