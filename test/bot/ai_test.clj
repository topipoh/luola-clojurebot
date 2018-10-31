(ns bot.ai-test
  (:require [clojure.test :refer :all]
            [bot.ai :refer :all]))

(deftest test-get-action
  (testing "AI should not walk into walls"
    (let [board "#####
                 #@..#
                 #####"]
      (is (= {:action "move"
              :direction "east"}
             (get-action board))))))
