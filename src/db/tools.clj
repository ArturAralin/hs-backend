(ns db.tools
  (:import [java.time LocalDate]))

;; convert ISO8601 to LocalDate
(defn parse-date [string]
  (when (string? string)
    (LocalDate/parse string)))

(defn parse-date-in [x path]
  (let [v (get-in x path)]
    (if (some? v)
      (update-in x path parse-date)
      x)))

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
