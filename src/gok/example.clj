(ns gok.example
  (:use [gok.core])
  (:require [clojure.string :as string]))

;;; example stuff
(defn file-source [fname]
  (string/split-lines (slurp fname)))

(defn file-contents [fname]
  (string/trim-newline (slurp fname)))

(defn tokenise [s]
  (string/split s #" "))

(defn token-count
  ([tokens] (token-count tokens {}))
  ([tokens acc]
     (if (empty? tokens) acc
         (recur (next tokens) (merge-with + acc {(first tokens) 1})))))

(defn standard [fname]
  (time (->> (file-source fname)
             (map file-contents)
             (map tokenise)
             (map token-count)
             (cons +)
             (apply merge-with)
             (last))))

(defn gok-enabled [fname]
  (time (-> (p-source (file-source fname))
            (one-by-one (file-contents))
            (one-by-one (tokenise))
            (one-by-one (token-count))
            (p-sink (merge-with +))
            (last))))
