(ns proj
  (:require [miraj.core :as miraj :refer [deflibrary]]))
            ;; [miraj.html :as h]
            ;; [miraj.html.protocols :as hp]))

;; (println "loading proj")

(deflibrary gadgets
  "Miraj component library: proj.widgets"
  #:miraj{:require '[[proj.widgets :export :all]]
          :defelements true})
