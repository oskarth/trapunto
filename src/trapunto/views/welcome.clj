(ns trapunto.views.welcome
  (:require [trapunto.views.common :as common])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]
        [hiccup.form :only [form-to text-area submit-button]]))
               
(defpage "/" []
  (common/layout
   [:p "Welcome to trapunto"]
   (form-to [:post "/"]
            (text-area {:class "code"} "code" "")
            (submit-button "Submit"))
  [:img {:src "/img/trapunto1.jpg" :width "80" :height "80" :float "left"}]))

(defpage [:post "/"] {:keys [code]}
  ;; take code, pipe to quil in sandox
  ;; then use that image to refresh it only on the same page
  (println code))
