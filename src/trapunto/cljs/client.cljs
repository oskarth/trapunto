(ns trapunto.client
 (:use [jayq.util :only [map->js]]))

(declare compare-code)
(def input nil)

(defn ^:export timer [delay-ms]
  (js/setInterval compare-code delay-ms))

(defn ^:export codemirror []
  (set! input
        (js/CodeMirror.fromTextArea (.getElementById js/document "visible")
                                    (map->js {:mode "text/x-clojure"}))))

(def xhr (new js/XMLHttpRequest))

(defn replace-image []
  ;; check if response is OK and finished loading
  (if (and (= (. xhr -readyState) 4)
           (= (. xhr -status) 200))
    (.. js/document (getElementById "output")
        (setAttribute "src" (str "/output/" (str (. xhr -response)) ".png")))))

(defn compare-code []
  (let [visible (. input getValue)]
    (if-not (= visible (.. js/document (getElementById "invisible") -value))
      (do (set! (.. js/document (getElementById "invisible") -value)  visible)
          (doto xhr
            (. open "POST" "/" false) 
            (. setRequestHeader "Content-Type" "text/plain")
            (. send visible))
          (set! (. xhr -onreadystatechange) replace-image)))))
