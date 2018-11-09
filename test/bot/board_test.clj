(ns bot.board-test
  (:require [clojure.test :refer :all]
            [bot.board :refer :all]))

(deftest test-get-neighbors
  (let [board "..########
               .........#
               #....@#..#
               #.....#$.#
               ##########"]
    (is (= #{ {:x 1 :y 0} {:x 0 :y 1}}
           (get-neighbors board {:x 0 :y 0})))
    (is (= #{ {:x 7 :y 3} {:x 7 :y 1} {:x 8 :y 2}}
           (get-neighbors board {:x 7 :y 2})))))
