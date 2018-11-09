(ns bot.board
  (:require [clojure.string :as str]))

(defn parse-board [board]
  (->> board
       (str/split-lines)
       (map char-array)
       (map seq)))

(defn get-neighbors [board location]
  #{})
