(ns bot.board
  (:require [clojure.string :as str]))

(defn parse-board [board]
  (->> board
       (str/split-lines)
       (map char-array)
       (map seq)))

(def walkable-tiles
  #{\. \$})

(defn walkable? [tile]
  (contains? walkable-tiles tile))

(defn maybe-get [board x y]
  (some-> board
          (get y)
          (get x)))

(defn get-neighbors [board location]
  #{})
