
(ns bot.dijkstra
  (:require [shams.priority-queue :as pq])
  (:require [bot.board :as board]))

(def priority-fn (fn [[_ cost]] (- cost)))

(defn- create-queue [elements] (pq/priority-queue priority-fn :elements elements))

; https://www.redblobgames.com/pathfinding/a-star/introduction.html#dijkstra
; frontier = PriorityQueue()
; frontier.put(start, 0)
; came_from = {}
; cost_so_far = {}
; came_from[start] = None
; cost_so_far[start] = 0
;
; while not frontier.empty():
;    current = frontier.get()
;
;    if current == goal:
;       break
;
;    for next in graph.neighbors(current):
;       new_cost = cost_so_far[current] + graph.cost(current, next)
;       if next not in cost_so_far or new_cost < cost_so_far[next]:
;          cost_so_far[next] = new_cost
;          priority = new_cost
;          frontier.put(next, priority)
;          came_from[next] = current


(defn- inner-loop [frontier came-from cost-so-far costs current next]
  (let [new-cost (+ (get cost-so-far current) (get costs next))]
    (if (or (not (get cost-so-far next))
            (< new-cost (get cost-so-far next)))
      [(conj frontier [next new-cost])
       (assoc came-from next current)
       (assoc cost-so-far next new-cost)
       costs
       current]
      [frontier
       came-from
       cost-so-far
       costs
       current])))

(defn- reduce-fn [ [frontier came-from cost-so-far costs current] next ]
  (inner-loop frontier came-from cost-so-far costs current next))

; TODO: not happy with the implementation, could be simpler?
(defn dijkstra [board start]
  (loop [frontier (create-queue [[start 0]])
         came-from {start nil}
         cost-so-far {start 0}
         costs (board/costs board)]
    (if (not (empty? frontier))
      (let [[current _] (peek frontier)
            neighbors (board/get-neighbors board current)
            [frontier came-from cost-so-far costs] (reduce reduce-fn [(pop frontier) came-from cost-so-far costs current] neighbors) ]
        (recur frontier came-from cost-so-far costs))
      {:came-from came-from
       :cost-so-far cost-so-far})))
