(defproject aws-cred-example "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [reagent "0.5.0"]]

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-figwheel "0.3.3"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src/cljs"]
                        :figwheel     true
                        :compiler     {:main                 aws-cred-example.core
                                       :output-to            "resources/public/js/compiled/app.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :asset-path           "js/compiled/out"
                                       :source-map-timestamp true}}

                       {:id           "min"
                        :source-paths ["src/cljs"]
                        :compiler     {:main          aws-cred-example.core
                                       :output-to     "resources/public/js/compiled/app.js"
                                       :output-dir    "resources/public/js/compiled/out-min"
                                       :source-map    "resources/public/js/compiled/app.js.map"
                                       :optimizations :advanced
                                       :pretty-print  false
                                       :pseudo-names  true
                                       :externs       ["resources/private/js/externs.js"]}}]})
