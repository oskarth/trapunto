(defproject trapunto "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.3.0-beta8"]
                           [clj-time "0.4.3"]
                           [quil "1.4.1"]
                           [clojail "0.5.0"]]
            :dev-dependencies [[lein-cljsbuild "0.2.1"]]
            :cljsbuild
            {:builds
             [{:source-path "src/trapunto/cljs/"
               :compiler
               {:output-to "resources/public/js/cljs.js"
                :optimizations :simple
                :pretty-print true}}]}
            :main trapunto.server)

