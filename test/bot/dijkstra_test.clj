(ns bot.dijkstra-test
  (:require [clojure.test :refer :all]
            [bot.dijkstra :refer :all]
            [bot.test-utils :refer :all]))

(deftest should-find-route-to-treasure
  (let [board (bot.board/parse-board (load-resource "case3.txt"))
        location1 {:x 5 :y 2}
        location2 {:x 5 :y 1}
        location3 {:x 6 :y 1}
        location4 {:x 7 :y 1}
        location5 {:x 7 :y 2}
        location6 {:x 7 :y 3}
        {:keys [came-from cost-so-far]} (dijkstra board location1)]
    (do
      (is (= location1 (get came-from location2)))
      (is (= location2 (get came-from location3)))
      (is (= location3 (get came-from location4)))
      (is (= location4 (get came-from location5)))
      (is (= location5 (get came-from location6)))

      (is (= 0 (get cost-so-far location1)))
      (is (= 1 (get cost-so-far location2)))
      (is (= 2 (get cost-so-far location3)))
      (is (= 3 (get cost-so-far location4)))
      (is (= 4 (get cost-so-far location5)))
      (is (= 5 (get cost-so-far location6)))

      (println (clojure.pprint/pprint (costs-array cost-so-far board))))))

(deftest should-prefer-safe-treasure
  (let [board (bot.board/parse-board (load-resource "case7.txt"))
        safe-location {:x 2 :y 4}
        dangerous-location {:x 8 :y 1}
        most-dangerous-location {:x 11 :y 2}
        {:keys [came-from cost-so-far]} (dijkstra board {:x 6 :y 2})]
    (do
      (println (clojure.pprint/pprint (costs-array cost-so-far board)))
      (is (< (get cost-so-far safe-location)
             (get cost-so-far dangerous-location)
             (get cost-so-far most-dangerous-location))))))

(deftest test-backtrack
  (let [board (bot.board/parse-board (load-resource "case3.txt"))
        location1 {:x 5 :y 2}
        location2 {:x 5 :y 1}
        location3 {:x 6 :y 1}
        location4 {:x 7 :y 1}
        location5 {:x 7 :y 2}
        location6 {:x 7 :y 3}
        {:keys [came-from cost-so-far]} (dijkstra board location1)]
    (is (= location2 (backtrack came-from location1 location6)))
    (is (= location3 (backtrack came-from location2 location6)))
    (is nil? (backtrack came-from {:x 10 :y 3} location6))))

(deftest dijkstra-performance
  (time (let [board (bot.board/parse-board (load-resource "case8.txt"))
              my-location (bot.board/my-location board)
              {:keys [came-from cost-so-far]} (dijkstra board my-location)])))
