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
    (is (= \. (maybe-get board {:x 0  :y 0})))
    (is (= \e (maybe-get board {:x 1  :y 1})))
    (is (nil? (maybe-get board {:x 1  :y 2})))
    (is (nil? (maybe-get board {:x -1 :y 0})))))

(deftest test-cardinal-directions
  (is (= #{{:x -1 :y 0}
           {:x 1  :y 0}
           {:x 0  :y -1}
           {:x 0  :y 1}}
         (cardinal-directions {:x 0 :y 0}))))

(deftest test-get-neighbors
  (let [board (parse-board (load-resource "case6.txt"))]
    (is (= #{ {:x 1 :y 0} {:x 0 :y 1}}
           (get-neighbors board {:x 0 :y 0})))
    (is (= #{ {:x 7 :y 3} {:x 7 :y 1} {:x 8 :y 2}}
           (get-neighbors board {:x 7 :y 2})))))

(deftest test-costs
  (let [board (parse-board (load-resource "case7.txt"))
        safe-location {:x 2 :y 4}
        dangerous-location {:x 8 :y 1}
        most-dangerous-location {:x 11 :y 2}
        cost-map (costs board)]
    (is (< (get cost-map safe-location)
           (get cost-map dangerous-location)
           (get cost-map most-dangerous-location)))))

(deftest test-my-location
  (let [board (parse-board (load-resource "case2.txt"))]
    (is (= {:x 9 :y 8} (my-location board)))))
