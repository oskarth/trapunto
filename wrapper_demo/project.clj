(defproject quilwrap "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [quil "1.4.1"]
                           [org.clojars.processing-core/org.processing.core "1.5.1"]]
            :dev-dependencies [[lein-swank "1.4.4"]
                               [lein-cljsbuild "0.2.1"]]
            :exclusions [swank-clojure]
            :main quilwrap)

