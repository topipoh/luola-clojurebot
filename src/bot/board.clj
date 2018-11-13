(ns bot.board
  (:require [clojure.string :as str]))

(defn parse-board [board-string]
  (vec (->> board-string
            (str/split-lines)
            (map char-array)
            (map vec))))

(def walkable-tiles
  #{\. \$})

(defn walkable? [tile]
  (contains? walkable-tiles tile))

(defn maybe-get [board {:keys [x y]}]
  (some-> board
          (get y)
          (get x)))

(defn cardinal-directions [{:keys [x y]}]
  #{{:x (dec x) :y y}
    {:x (inc x) :y y}
    {:x x       :y (dec y)}
    {:x x       :y (inc y)}})

(defn debug [value]
  (do
    (println value)
    value))

(defn to-set [coll]
  (when (seq coll)
    (set coll)))

(defn get-neighbors [board location]
  (->> location
       (cardinal-directions)
       (filter #(walkable? (maybe-get board %)))
       (to-set)))
