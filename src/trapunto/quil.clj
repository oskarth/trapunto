;; maybe use Runnable to launch this instead
;;   of running it in a separate program?

(ns trapunto
  (:use quil.core clojail.core))

(def *output-prefix*)

;; maybe parse out defns from user input and insert them here
;;   to be used in "main", i.e. draw

(defn setup []
  ;; set up black on white, 1FPS, static image
  (smooth)
  (frame-rate 1)
  (background 255)
  (stroke 0)
  (fill 0)
  (noLoop))

;; insert draw before base-level code, and save after it
(defn draw []
  ((sandbox secure-tester)
   ;; insert user code here
   )
  (save (str *output-prefix* ".png")) ;; insert file name to upload here
)

(defsketch
  :title "trapunto"
  :setup setup
  :draw draw
  :size [200 200])

(comment
  ;; on the server...
  (binding [*ns* 'temp]
    (try
      (load-string (recieve-from-server-function))  ;; input from server
      (catch java.lang.Exception e
        (don't-save-function) ;; push forward an empty draw function
                              ;;   with no saving, no refreshing image
        ))))
