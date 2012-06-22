(ns trapunto.core
  (:use compojure.core
        trapunto.views
        [trapunto.draw :only [output-image]])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

;; TODO rename to routes?

(defroutes main-routes
  (GET "/" [] (index-page))
  (POST "/" {body :body} (output-image body))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))
