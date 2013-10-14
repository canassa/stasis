(ns stasis.core-test
  (:require [clojure.test :refer :all]
            [stasis.core :refer :all]))


(def simple-frontmatter "---
layout: post
title: Blogging Like a Hacker
---
# Test
")


(def frontmatter-with-extra-dashes "---
layout: post
title: Blogging Like a Hacker
---
# Test
---
## Test2
")


(deftest test-parse-frontmatter
  (testing "YAML parsing"
    (is (= (:context (parse-frontmatter simple-frontmatter))
           {:layout "post", :title "Blogging Like a Hacker"})))

  (testing "Markdown should be parsed"
    (is (= (:body (parse-frontmatter simple-frontmatter))
           "<h1>Test</h1>")))

  (testing "Markdown should be parsed even without frontmatter"
    (is (= (:body (parse-frontmatter "## Test"))
           "<h2>Test</h2>")))

  (testing "The YAML regex should not be greedy"
    (is (= (:context (parse-frontmatter frontmatter-with-extra-dashes))
           {:layout "post", :title "Blogging Like a Hacker"}))))
