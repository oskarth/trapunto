(ns trapunto.draw
  (:use [quil.core]
        [clojure.string :only [split]]
        [clj-time.core :only [now]]
        [clj-time.coerce :only [to-long]]
        
        ;; Eventually implement Clojail and rewrite long loops to help prevent
        ;;   security problems
        #_[clojail.core]
        #_[clojail.testers :only [secure-tester]])
  (import [processing.core PApplet]))

;;; Interleave two sequences, padding the smaller one. *Not lazy*
(defn padded-interleave-two [c1 c2 pad]
  (let [cc1 (count c1)
        cc2 (count c2)]
    (if (> cc1 cc2)
      (interleave c1 (into c2 (repeat (- cc1 cc2) pad)))
      (interleave (into c1 (repeat (- cc2 cc1) pad)) c2))))

;;; Extract all of the functions corresponding to Processing functions from Quil
(def processing-funs
  (loop [m {}
         fs (map first (filter #(:processing-name (meta (second %)))
                               (ns-publics 'quil.core)))]
    (if (empty? fs) m
        (recur (assoc m (str (first fs))
                      (str ". trapunto.draw/g " (first fs)))
               (rest fs)))))

;;; Replace a Processing function with its equivalent or leave it alone
(defn replace-processing [regex-result]
  (let [name (second regex-result)
        replacement (get processing-funs name)]
    (str "("  (if (nil? replacement) name replacement))))


(defn draw-image [input [width height] filename]
  (let [context (. (PApplet.) createGraphics width height PApplet/P2D)]
    ;; Stick our PGraphics object that corresponds to the replaced functions
    ;;   above in the namespace 
    (intern 'trapunto.draw 'g context)
    (. context beginDraw)
    ;; Write a blank white image by default
    (. context background 255 255 255)
    (let [pattern (re-pattern #"\( *([^\"\( ]*)")
          ;; Wrap the whole of the input in a do statement for user convenience
          result (str "(do "
                      ;; Interleave replaced function names with the text in
                      ;;   between, i.e. arguments and parentheses
                      (apply str (padded-interleave-two
                                  (map replace-processing
                                       (re-seq pattern input))
                                  (split input pattern) "")) ")")]
      (try
        (eval (read-string result))
        (. context endDraw)
        (. context save filename)
        ;;; TODO: Do not save a file when an exception is caught from draw-image,
        ;;;   and do not change the image on the server!
        #_(catch Exception ex)
        (finally
         (. context endDraw))))))

(defn gen-name [] (to-long (now)))

;;; TODO: Do not save a file when an exception is caught from draw-image,
;;;   and do not change the image on the server!
(defn output-image [instream]
  (let [filename (str (gen-name))
        input (slurp instream)]
    (draw-image input [200 200]
                (str "resources/public/output/" filename ".png"))
    filename))
