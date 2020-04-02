(ns db.tools
  (:import [java.time LocalDate]))

;; convert ISO8601 to LocalDate
(defn parse-date [string]
  (LocalDate/parse string))

(defn resolve-nulls [row]
  (->> row
       (filter (fn [[_ v]] (some? v)))
       (map (fn [[k v]] [k (if (= v :null) nil v)]))
       (into {})))

(defn nil-to-null-keyword [row]
  (->> row
       (map (fn [[k v]] [k (if (nil? v) :null v)]))
       (into {})))

(defn try-int [v]
  (try
    (Integer. v)
    (catch Exception _ nil)))
