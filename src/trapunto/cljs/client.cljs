(ns trapunto)

(defn compare-code []
  (let [visible (.. js/document (getElementById "visible") -value)]
    (if-not (= visible (.. js/document (getElementById "invisible") -value))
      (set! (.. js/document (getElementById "invisible") -value) visible))))

(defn ^:export timer [delay-ms]
  (js/setInterval compare-code delay-ms))

