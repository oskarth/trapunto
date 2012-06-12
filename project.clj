(defproject trapunto "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [compojure "1.1.0"]
                           ;;[noir "1.3.0-beta8"] ;; remove
                           [clj-time "0.4.3"]
                           [quil "1.4.1"]
                           [clojail "0.5.0"]
                           [hiccup "1.0.0"]]
            :dev-dependencies [[lein-ring "0.7.0"]
                               [lein-cljsbuild "0.2.1"]]
;;            :plugins [[lein-ring "0.7.0"]]
            :cljsbuild
            {:builds
             [{:source-path "src/trapunto/cljs/"
               :compiler
               {:output-to "resources/public/js/cljs.js"
                :optimizations :simple
                :pretty-print true}}]}
            :ring {:handler trapunto.core/app})
;;            :main trapunto.server) ;; remove?
