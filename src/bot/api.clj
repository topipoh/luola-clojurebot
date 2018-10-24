(ns bot.api
  (require [clojure.tools.cli :refer [parse-opts]]))

(defn add-bot
  "Add bot to the game"
  [base-url pass name]
  (println "Adding bot" name pass base-url))
