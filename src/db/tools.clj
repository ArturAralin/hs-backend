(ns db.tools
  (:import [java.time LocalDate]))

;; convert ISO8601 to LocalDate
(defn parse-date [string]
  (LocalDate/parse string))
