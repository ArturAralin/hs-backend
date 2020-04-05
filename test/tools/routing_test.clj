(ns tools.routing_test
  (:require
   [clojure.test :refer [deftest testing is]]
   [clojure.spec.alpha :as s]
   [tools.routing :refer [with-validation]]))

(s/def ::id int?)
(s/def ::ids (s/coll-of #(s/valid? ::id %)))

(def wrapped-handler
  (with-validation
    (fn [_] {:status :ok})
    {:body ::ids}))

(deftest tools-routing
  (testing "with-validation"
    (is (= (wrapped-handler {:body [1 2 3]}) {:status :ok}))
    (let [status (:status (wrapped-handler {:body ["a" "b" "c"]}))]
      (is (= status 400)))))
