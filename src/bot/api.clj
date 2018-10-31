(ns bot.api
  (:require [clj-http.client :as client]))

(defn add-player
  "Add bot to the game"
  [base-url pass name]
  (do
    (println "Adding player" name pass base-url)
    (client/get
      (str base-url "/api/add-player")
      {:query-params {"name" name
                      "pass" pass}})))

(defn get-board
  [base-url name]
  (:body
    (client/get
      (str base-url "/api/board")
      {:query-params {"name" name}})))

(defn act
  [base-url pass name action target]
  (client/get
    (str base-url "/api/act")
    {:query-params {"name" name
                    "pass" pass
                    "action" action
                    "target" target}}))

(defn turn-duration
  "Return turn duration as number"
  [base-url]
  (read-string
    (:body
      (client/get
        (str base-url "/api/turn-duration")))))
