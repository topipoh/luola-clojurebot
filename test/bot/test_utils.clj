(ns bot.test-utils
  (:require [clojure.java.io :as io]))

(defn load-resource [filename]
  (slurp (io/resource filename)))

