(ns trapunto.client
 (:use [jayq.util :only [map->js]]))

(declare compare-code)

(defn ^:export timer [delay-ms]
  (js/setInterval compare-code delay-ms))

(defn ^:export codemirror []
  (js/CodeMirror.fromTextArea (.getElementById js/document "visible")
                              (map->js {:mode "text/x-clojure"})))
;; from ibdknox live-cljs, need more?

(def xhr (new js/XMLHttpRequest))

(defn replace-image []
  ;; check if response is OK and finished loading
  (if (and (= (. xhr -readyState) 4)
           (= (. xhr -status) 200))
    (.. js/document (getElementById "output")
        ;; TODO Image URLs hit the page and refresh before the image is
        ;;   finished writing, preventing the image from displaying at all
        (setAttribute "src" (str "/img/" (str (. xhr -response)) ".png")))))

(defn compare-code []
  (let [visible (.. js/document (getElementById "visible") -value)]
    (if-not (= visible (.. js/document (getElementById "invisible") -value))
      (do (set! (.. js/document (getElementById "invisible") -value)  visible)
          (doto xhr
            (. open "POST" "/" false) 
            (. setRequestHeader "Content-Type" "text/plain")
            (. send visible))
          (set! (. xhr -onreadystatechange) replace-image)))))
