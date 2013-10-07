(ns stasis.template
  (:use [hiccup core util page]
        [stasis.core :only (templates)]))


(def ^:dynamic block)


(defn extends
  [template-key block-map]
  (binding [block #(seq (%1 block-map))]
    (eval (get templates template-key))))
