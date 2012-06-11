(ns trapunto.views.welcome
  (:require [trapunto.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" []
         (common/layout
           [:p "Welcome to trapunto"]))
