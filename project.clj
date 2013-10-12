(defproject stasis "0.1.0-SNAPSHOT"
  :description "A (WIP) Clojure library designed to help generaing static site generators."
  :url "https://github.com/canassa/stasis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccup "1.0.4"]
                 [markdown-clj "0.9.33"]
                 [clj-yaml "0.4.0"]]
  :main stasis.core)
