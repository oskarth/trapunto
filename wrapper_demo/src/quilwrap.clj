(ns quilwrap
  (:use [clojure.string :only [split]]
        [quil.core])
  (import [processing.core PApplet]
          [java.util.regex])
  (:gen-class))

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
                      (str ". g " (first fs)))
               (rest fs)))))

;;; Replace a Processing function with its equivalent or leave it alone
(defn replace-processing [regex-result]
  (let [name (second regex-result)
        replacement (get processing-funs name)]
    (str "("  (if (nil? replacement) name replacement))))


(defn sketch-out [[width height] filename]
  (let [input (str "(do (background 0 0 0) (color 255 255 255))",
                    "(stroke 255 255 255)"
                    "(line 10 10 150 150)"
                    "(line 150 120 100 10)")
        context (. (PApplet.) createGraphics width height PApplet/P2D)]
    (intern 'quilwrap 'g context)
    (. context beginDraw)
    ;; Write a black image by default
    (. context background 0 0 0)
    (let [pattern (re-pattern #"\( *([^\"\( ]*)")
          ;; Wrap the whole of the input in a do statement
          result (str "(do "
                      (apply str (padded-interleave-two
                                  (map replace-processing
                                       (re-seq pattern input))
                                  (split input pattern) "")) ")")]
      (try
        (eval (do (in-ns 'quilwrap) (read-string result)))
        ;; In the real program, we'll bomb on any exception and leave the previous
        ;;   image in place
        #_(catch Exception ex)
        (finally
         (. context endDraw)
         ;; We need to call endDraw no matter what, but also before saving
         (. context save filename))))))

(defn -main [& args]
  (sketch-out [200 200] "out.png"))
