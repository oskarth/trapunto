(ns trapunto)

(defn ^:export timer [delay-ms]
  (js/setInterval compare-code delay-ms))

(def xhr (new js/XMLHttpRequest))

;;; Eventually replace with Data URI
(defn replace-image []
  (if (and (= (. xhr -readyState) 4)
           (= (. xhr -status) 200)
           (do
             ;; temporary testing value
             (set! (.. js/Document (getElementById "visible") -value)
                   "Works!"))

           ;; actual image replacement code
           (comment (.. js/document (getElementById "output")
                (setAttribute "src" ("url" (js->clj (. xhr -responseText)))))))))

(defn compare-code []
  (let [visible (.. js/document (getElementById "visible") -value)]
    (if-not (= visible (.. js/document (getElementById "invisible") -value))
      (do (set! (.. js/document (getElementById "invisible") -value)  visible)
          (doto xhr
            (comment (set! (. -onreadystatechange replace-image)))
            (. open "POST" "/" false) 
            (. setRequestHeader "Content-Type" "application/json")
            (. send {:code visible}))
          (set! (. xhr -onreadystatechange) replace-image)
          ))))


