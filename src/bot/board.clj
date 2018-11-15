(ns bot.board
  (:require [clojure.string :as str]))

(defn parse-board [board-string]
  (vec (->> board-string
            (str/split-lines)
            (map char-array)
            (map vec))))

(def walkable-tiles
  #{\. \$})

(def monster-tile \e)

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

(defn- get-tiles-for-line [y line]
  (map-indexed
    (fn [idx itm] {:x idx :y y :tile itm})
    line))

(defn get-tiles [board]
  (flatten
    (map-indexed
      (fn [idx item] (get-tiles-for-line idx item))
      board)))

(defn locations-by-type [board tile-char]
  (->> board
       (get-tiles)
       (filter #(= tile-char (:tile %)))))

(defn monsters [board]
  (locations-by-type board monster-tile))

(defn absolute-difference [a b]
  (Math/abs (- b a)))

(defn manhattan-distance [a b]
  (+ (absolute-difference (:x a) (:x b))
     (absolute-difference (:y a) (:y b))))

(defn cost-for-location [location monsters]
  (+ 1 (->> monsters
            (map #(manhattan-distance location %))
            (filter #(< % 4))
            (map #(- 4 %))
            (reduce +))))

(defn costs [board]
  (let [monsters (monsters board)]
    (->> (get-tiles board)
         (reduce
           (fn [result-map location]
             (assoc result-map
               (select-keys location [:x :y])
               (cost-for-location location monsters)))
           {}))))
