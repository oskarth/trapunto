(ns trapunto.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-js include-css html5]]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "trapunto"]
    (include-css "/css/reset.css")
    (include-js "/js/cljs.js")]
   [:body
    [:div#wrapper
     content]]))
