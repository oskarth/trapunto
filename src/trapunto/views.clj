(ns trapunto.views
  (:use
   ;; [noir.core :only [defpage defpartial]]
   ;; [noir.response :only [empty json]]
        [hiccup.core :only [html]]
        [hiccup.form :only [form-to text-area submit-button]]
        [hiccup.page :only [include-js include-css html5]]))

(defn index-page []
  (html5
   [:head
    [:title "Trapunto"]
    (include-css "/css/reset.css")
    (include-js "/js/cljs.js")]
   [:body
    [:h1 "Trapunto"]
    (form-to [:post "/"]
             (text-area {:id "visible"} "code" "")
             (text-area {:id "invisible"} "invis" "")
             (submit-button "Submit"))
    [:img {:id "output" :src "/img/trapunto1.jpg"
           :width "200" :height "200" :float "left"}]
    [:script {:type "text/javascript"} "trapunto.client.timer(20000);"]]))
