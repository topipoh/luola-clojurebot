(ns bot.core
  (require [clojure.tools.cli :refer [parse-opts]])
  (require [bot.api :refer :all]))

(def required-opts #{})

(defn missing-required?
  "Returns true if opts is missing any of the required-opts"
  [opts]
  (not-every? opts required-opts))

;base url http://localhost:8080
(defn -main [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args
                                                               [["-h" "--help" "Print this help" :default false]
                                                                ["-u" "--url URL" "Base URL for Luola server" :default "http://localhost:8080"]
                                                                ["-b" "--bots BOTS" "Number of bots to spawn" :parse-fn #(Integer. %) :default 1]
                                                                ["-p" "--pass PASS" "Bot password" :default "MySecret"]
                                                                ["-n" "--name NAME" "Name of the bot" :default "delet0r"]])]
    (if (or (:help options)
            (missing-required? options))
      (println summary)
      (do
        (println options)
        (add-bot (:url options) (:pass options) (:name options))))))
