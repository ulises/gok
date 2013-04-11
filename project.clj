(defproject gok "0.0.1-SNAPSHOT"
  :description "Cool new project to do things and stuff"
  :warn-on-reflection true
  :jvm-opts ["-Xmx1g"]
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles {:dev {:dependencies [[midje "1.5.0"]]}})
