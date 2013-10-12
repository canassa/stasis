(ns stasis.core
  (:require [clj-yaml.core :as yaml]
            [clojure.java.io :as io])
  (:use [hiccup core util page]
        [markdown.core :only (md-to-html-string)])
  (:import [org.apache.commons.io FilenameUtils]))


(def base-dir (System/getProperty "user.dir"))


(defn list-files
  "Returns a list of files under a given directory, accepts an optional
  :ext keyword that filters the results bases on the extension."
  [dir & {ext :ext}]
  (let [files (map #(.toString %1)
                   (filter #(.isFile %1)
                           (file-seq (io/file base-dir dir))))]
    (if ext
      (filter #(= (FilenameUtils/getExtension %1) ext) files)
      files)))


(defn render-template
  "Renders a clojure template using the stasis.template namespace for evaluation"
  [template]
  (binding [*ns* *ns*]
    (in-ns 'stasis.template)
    (eval template)))


(defn read-template
  "Reads a template file and returns its data structure"
  [path]
  (-> path
      (slurp)
      (read-string)))


(defn process-file [filename]
  (with-open [reader (io/reader (io/file base-dir filename))]
    (count (line-seq reader))))


(defn parse-frontmatter
  [data]
  (let [[_ header body] (re-find #"(?s)^---\n(.*)---\n(.*)" data)]
    (if header
      {:body body :context (yaml/parse-string header)}
      {:body data})))


; templates is a map of the templates data structures
; the key is generated from the template file name .e.g.: {:base (html [:h1 "Hi!"])}
(def templates
  (let [files (list-files "blog/templates")]
    (zipmap (map #(keyword (FilenameUtils/getBaseName %1)) files)
            (map read-template files))))


(defn -main []
  (println "main"))
