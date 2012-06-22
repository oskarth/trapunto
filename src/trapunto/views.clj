(ns trapunto.views
  (:use
    [hiccup.core :only [html]]
    [hiccup.form :only [form-to text-area submit-button]]
    [hiccup.page :only [include-js include-css html5]]))

(defn index-page []
  (html5
    [:head
     [:title "Trapunto"]
     (include-css "/css/reset.css")
     (include-css "/css/codemirror.css")
     (include-js "/js/codemirror.js")
     (include-js "/js/clojure.js")
     (include-js "/js/cljs.js")]
    [:body
     [:h1 "Trapunto"]
     [:div#outer-wrapper
      [:div 
       (form-to [:post "/"]
                (text-area {:id "visible"} "code" "")
                (text-area {:id "invisible" :hidden "true"} "invis" ""))]
      [:div#canvas 
       [:img {:id "output" :src "/img/trapunto1.jpg" :width 200 :height 200
              :float "right"}]]]
     [:script {:type "text/javascript"} "trapunto.client.timer(20000);"]
     [:script {:type "text/javascript"} "trapunto.client.codemirror();"]]))
