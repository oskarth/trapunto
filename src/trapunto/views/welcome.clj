(ns trapunto.views.welcome
  (:require [trapunto.views.common :as common])
  (:use [noir.core :only [defpage defpartial]
         [noir.response :only [empty json]]
         [hiccup.core :only [html]]
         [hiccup.form :only [form-to text-area submit-button]]]))

(defpage "/" []
  (common/layout
   [:p "Welcome to trapunto"]
   (form-to [:post "/"]
            (text-area {:id "visible"} "")
            (text-area {:id "invisible"} "")
            (submit-button "Submit"))
   [:img {:src "/img/trapunto1.jpg" :width "80" :height "80" :float "left"}]
   [:script {:type "text/javascript"} "trapunto.timer(2000);"]))

(defpage [:post "/"] {}
  (empty))

#_(comment (defpage [:post "/"] {:keys [code]}
   ;; take code, pipe to quil in sandox
   ;; then use that image to refresh it only on the same page
   (println code)))
