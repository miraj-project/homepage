(ns lib.msgs
  (:refer-clojure :exclude [list])
  (:require [miraj.core :as miraj :refer [defcomponent]]
            [miraj.html :as h]
            [miraj.polymer :as p]
            [miraj.polymer.protocols]
            [miraj.polymer.iron :as iron :refer [icon]]
            ;;[miraj.core :as wc]
            #_[miraj.co-dom :as x]))

;; for testing only:
;; [miraj.co-dom :as codom]
;; #_:reload))

(println "loading lib.msgs")

(miraj/defcomponent important :html msg-important
  "Important msg"

  (:codom
   (h/div :.wrapper
    (iron/icon {:icon "notification:priority-high"})
    (p/slot)))

  ;; {:polymer/properties {:clj {:value false :type Boolean}
  ;;                       :html {:value false :type Boolean}}}
  )

(miraj/defcomponent warning :html msg-warning
  "Important msg"

  (:codom
   (h/div :.wrapper
    (iron/icon {:icon "warning"})
    (p/slot)))
 
  ;; {:polymer/properties {:clj {:value false :type Boolean}
  ;;                       :html {:value false :type Boolean}}}
  )

(println "loaded lib.msgs")
