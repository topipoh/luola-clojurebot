(ns bot.ai
  (:require [bot.dijkstra :refer :all])
  (:require [bot.board :refer :all]))

(defn closest-treasure [board cost-so-far]
  (->> (treasures board)
       (filter #(get cost-so-far %))
       (sort-by #(get cost-so-far %))
       (first)))

(defn get-action
  [board-string]
  (let [board (parse-board board-string)
        my-location (my-location board)
        {:keys [came-from cost-so-far]} (dijkstra board my-location)
        target (or (closest-treasure board cost-so-far)
                   (first (get-neighbors board my-location)))
        first-step (backtrack came-from my-location target)]
    {:action    "move"
     :direction (which-direction my-location first-step)}))
