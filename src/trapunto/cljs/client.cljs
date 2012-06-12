(ns trapunto)

;; TODO: replace filename with saved image
;; TODO: fix bad image reloaded (marked region?)

(defn ^:export timer [delay-ms]
  (js/setInterval compare-code delay-ms))

(def xhr (new js/XMLHttpRequest))

(defn replace-image []
  (let [filename "x"]
    ;; check if response is OK and finished loading
    (if (and (= (. xhr -readyState) 4)
           (= (. xhr -status) 200)
           (do
           (.. js/document (getElementById "output")
               (setAttribute "src" (str "/img/" (str filename) ".jpg"))))))))
               ;;("url" (js->clj (. xhr -responseText)))

(defn compare-code []
  (let [visible (.. js/document (getElementById "visible") -value)]
    (if-not (= visible (.. js/document (getElementById "invisible") -value))
      (do (set! (.. js/document (getElementById "invisible") -value)  visible)
          (doto xhr
            (. open "POST" "/" false) 
            (. setRequestHeader "Content-Type" "application/json")
            (. send {:code visible}))
          (set! (. xhr -onreadystatechange) replace-image)
          ))))


