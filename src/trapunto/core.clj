(ns trapunto.core
  (:use compojure.core
        trapunto.views)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

;; TODO rename to routes?

(defroutes main-routes
  (GET "/" [] (index-page))
  (POST "/" [] (future-call (fn [] "reply from server"))) ;; right syntax? {:keys [code]}?
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))