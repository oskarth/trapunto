(ns trapunto.core
  (:use compojure.core
        trapunto.views
        [trapunto.draw :only [output-image]])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

;; TODO rename to routes?

(defroutes main-routes
  (GET "/" [] (index-page))
  (POST "/" {body :body}
        (let [filename (output-image body)
              file (java.io.File. (str "resources/public/output/"
                                       filename ".png"))]
          (while (not (. file exists))
            (await-for 1000))
          filename))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))
