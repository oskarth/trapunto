;; maybe use Runnable to launch this instead
;;   of running it in a separate program?

(ns trapunto
  (:use [quil.core]
        [clojail.core]
        [clj-time.core :only [now]]
        [clj-time.coerce :only [to-long]]))

;;(def *output-prefix*)

;; maybe parse out defns from user input and insert them here
;;   to be used in "main", i.e. draw

(defn setup []
  ;; just called once at startup
  ;; set up black on white, 1FPS, static image
  (smooth)
  (frame-rate 1)
  (background 255)
  (stroke 0)
  (fill 0)
  (no-loop))

;; define random name
(defn- gen-name []
  (str (to-long (now))))

;; insert draw before base-level code, and save after it
(defn draw []
  (let [name (gen-name)]
;;  ((sandbox secure-tester)
   ;; insert user code here
  (stroke (random 255))             ;;Set the stroke colour to a random grey
  (stroke-weight (random 10))       ;;Set the stroke thickness randomly
  (fill (random 255))               ;;Set the fill colour to a random grey
  
  (let [diam (random 100)           ;;Set the diameter to a value between 0 and 100
        x    (random (width))       ;;Set the x coord randomly within the sketch
        y    (random (height))]     ;;Set the y coord randomly within the sketch
    (ellipse x y diam diam))       ;;Draw a circle at x y with the correct diameter
;;   ) ;; jail time ends here
  (save (str "resources/public/img/" name ".png")))) ;; insert file name to upload here
;; here we want to send back name to user

(defsketch example
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
