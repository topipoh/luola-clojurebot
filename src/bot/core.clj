(ns bot.core
  (require [clojure.tools.cli :refer [parse-opts]])
  (require [bot.api :refer :all]))

(def required-opts #{})

(defn missing-required?
  "Returns true if opts is missing any of the required-opts"
  [opts]
  (not-every? opts required-opts))

(defn main-loop
  [url pass name turn-duration]
  (while
    true
    (do
      (println (get-board url name))
      (act url pass name "move" "east")
      (Thread/sleep (/ turn-duration 2)))))

(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args
                                                               [["-h" "--help" "Print this help" :default false]
                                                                ["-u" "--url URL" "Base URL for Luola server" :default "http://localhost:8080"]
                                                                ["-b" "--bots BOTS" "Number of bots to spawn" :parse-fn #(Integer. %) :default 1]
                                                                ["-p" "--pass PASS" "Bot password" :default "MySecret"]
                                                                ["-n" "--name NAME" "Name of the bot" :default "delet0r"]])]
    (let [{:keys [help url pass name]} options]
      (if (or help
              (missing-required? options))
        (println summary)
        (do
          (println options)
          (add-player url pass name)
          (main-loop url pass name (turn-duration url)))))))
