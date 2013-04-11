(ns gok.core)

(defn later [item f]
  "Perform a computation on an item at a later stage."
  (let [p (promise)
        _ (future (deliver p (f item)))]
    p))

(defn each [items f]
  "Similar to pmap in that it will compute (f item) in parallel although this
   will immediately return a seq of promises, all of which will be delivered
   in the background."
  (map later items (repeat f)))

(defmacro p-source [expr]
  "Declare an expresion as a source of data."
  `(map later ~expr (repeat identity)))

(defmacro one-by-one [ps expr]
  "Deref and thread each promise through expr."
  `(each ~ps #(-> @% ~expr)))

(defmacro p-sink [ps expr]
  "Declare an expression as a sink of data."
  `(reduce #(~@expr %1 @%2) @(first ~ps) (next ~ps)))
