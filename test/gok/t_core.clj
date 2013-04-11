(ns gok.t-core
  (:use midje.sweet)
  (:use [gok.core]))

(facts "about sources, stages and sinks"
  (fact "file source list takes resources from a file"
    (file-source "test.txt") => ["a.txt" "b.txt"])

  (fact "file contents produces a string with the contents of the file"
    (file-contents "a.txt") => "foo bar")

  (fact "tokenise splits strings on whitespace and returns a collection of tokens"
    (tokenise "foo bar") => ["foo" "bar"])

  (fact "token-count counts the occurrence of each token in a collection"
    (token-count ["foo" "bar"]) => {"bar" 1 "foo" 1})

  (fact "token count of the contents of the first file"
    (token-count
     (tokenise
      (file-contents
       (first (file-source "test.txt"))))) => {"foo" 1
                                               "bar" 1})

  (fact "full pipeline"
    (->> (file-source "test.txt")
         (map file-contents)
         (map tokenise)
         (map token-count)
         (cons +)
         (apply merge-with)) => {"foo" 1
                                 "bar" 2
                                 "baz" 1}))
