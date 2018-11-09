(ns bot.board-test
  (:require [clojure.test :refer :all]
            [bot.board :refer :all]
            [bot.test-utils :refer :all]))

(deftest test-parse-board
  (let [board (load-resource "case1.txt")]
    (is (= [ [\# \# \# \# \#]
             [\# \@ \. \. \#]
             [\# \# \# \# \#]]
           (parse-board board)))))

(deftest test-walkable?
  (is (true? (walkable? \.)))
  (is (true? (walkable? \$)))
  (is (false? (walkable? \#)))
  (is (false? (walkable? \e))))

(deftest test-maybe-get
  (let [board [ [\. \$] [\# \e]]]
    (is (= \. (maybe-get board 0 0)))
    (is (= \e (maybe-get board 1 1)))
    (is (nil? (maybe-get board 1 2)))
    (is (nil? (maybe-get board -1 0)))))

(deftest test-get-neighbors
  (let [board (parse-board (load-resource "case6.txt"))]
    (is (= #{ {:x 1 :y 0} {:x 0 :y 1}}
           (get-neighbors board {:x 0 :y 0})))
    (is (= #{ {:x 7 :y 3} {:x 7 :y 1} {:x 8 :y 2}}
           (get-neighbors board {:x 7 :y 2})))))
