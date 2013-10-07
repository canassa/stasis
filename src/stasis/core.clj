(ns stasis.core
  (:use [hiccup core util page])
  (:import [org.apache.commons.io FilenameUtils]))


(def base-dir (System/getProperty "user.dir"))


(defn list-files
  "Returns a list of files under a given directory, accepts an optional
  :ext keyword that filters the results bases on the extension."
  [dir & {ext :ext}]
  (let [files (map #(.toString %1)
                   (filter #(.isFile %1)
                           (file-seq (clojure.java.io/file base-dir dir))))]
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


; templates is a map of the templates data structures
; the key is generated from the template file name .e.g.: {:base (html [:h1 "Hi!"])}
(def templates
  (let [files (list-files "blog/templates")]
    (zipmap (map #(keyword (FilenameUtils/getBaseName %1)) files)
            (map read-template files))))


(defn -main []
  (println "main"))
