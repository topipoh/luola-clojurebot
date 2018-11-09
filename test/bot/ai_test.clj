(ns bot.ai-test
  (:require [clojure.test :refer :all]
            [bot.ai :refer :all]))

(deftest should-not-run-into-walls
  (let [board "#####
               #@..#
               #####"]
    (is (= {:action "move"
            :direction "east"}
           (get-action board)))))

(deftest should-go-to-treasure
  (let [board "##########
               #........#
               #....@#..#
               #.....#$.#
               ##########"]
    (is (= {:action "move"
            :direction "north"}
           (get-action board)))))
