(ns quilwrap
  (:use [quil.core]
        [quil.applet])
  (import [processing.core PApplet])
  (:gen-class))

;;; Converts a call from a quil function (which calls the Processing
;;;   applet directly) into a call to the applet's PGraphics instance
(defmacro quil-wrap [ctxt & args]
  `(. ~ctxt ~@args))

(defn sketch-out [[width height] filename]
  (let [applet (PApplet.)
        context (. applet createGraphics width height PApplet/P2D)]
    (. context beginDraw)
    ;; Write a black image by default
    (. context background 0 0 0)
    (try
      (declare ^:dynamic *cxt*)
      (binding [*cxt* context]
        ;; User code begins; Surround every string (separated by '\n') call
        ;;   with a variation of (quil-wrap context ... )
        (doseq [expr ["(background 0 0 0)",
                      "(color 255 255 255)"
                      "(stroke 255 255 255)"
                      "(line 10 10 150 150)"]]
          (eval (read-string
                 (str "(quilwrap/quil-wrap quilwrap/*cxt* " expr ")"))))
        
        ;; Original wrapper, can't use this form with manually evaluated
        ;;   strings because of the local var context
        (quil-wrap context (line 150 120 100 10)))
      (catch Exception ex)
      (finally
       (. context endDraw)
       ;; We need to call endDraw no matter what, but also before saving
       (. context save filename)))))

(defn -main [& args]
  (sketch-out [200 200] "out.png"))
