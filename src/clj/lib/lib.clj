(ns lib
  (:require [miraj.core :as miraj :refer [deflibrary]]))


            ;; [miraj.html.protocols :as hp]))

;; (println "loading lib")

(deflibrary msg
  "Miraj component library: lib.msgs"
  #:miraj{:require '[[lib.msgs :export :all]]
          :defelements true})
