(ns trapunto.client)

;; TODO: howdy world use text as src
;; TODO: eval text (+ 3 4) and use as src
;; TODO: replace filename with saved image
;; TODO: fix bad image reloaded (marked region?)

(declare compare-code)

(defn ^:export timer [delay-ms]
  (js/setInterval compare-code delay-ms))

(def xhr (new js/XMLHttpRequest))

(defn replace-image []
  (let [filename "x"]
    ;; check if response is OK and finished loading
    (if (and (= (. xhr -readyState) 4)
             (= (. xhr -status) 200)
             (do
               ;; temporary testing value
               (set! (.. js/document (getElementById "visible") -value)
                     (str (. xhr -response)))

               ;; actual image replacement code
               (comment (.. js/document (getElementById "output")
                            (setAttribute
                             "src" (str "/img/" (str filename) ".jpg")))))))))

(defn compare-code []
  (let [visible (.. js/document (getElementById "visible") -value)]
    (if-not (= visible (.. js/document (getElementById "invisible") -value))
      (do (set! (.. js/document (getElementById "invisible") -value)  visible)
          (doto xhr
            (. open "POST" "/" false) 
            (. setRequestHeader "Content-Type" "text/plain")

            ;; Placeholder for generated image URL; random number to make it
            ;;   easy to detect success when testing
            (. send (. js/Math random)))
          (set! (. xhr -onreadystatechange) replace-image)))))
