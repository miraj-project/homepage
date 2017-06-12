(ns proj.widgets
  (:refer-clojure :exclude [list])
  (:require [miraj.core :as miraj :refer [defcomponent]]
            [miraj.html :as h]
            [miraj.polymer :as p]
            [miraj.polymer.protocols]
            ;;[miraj.core :as wc]
            #_[miraj.co-dom :as x]))

;; for testing only:
;; [miraj.co-dom :as codom]
;; #_:reload))

;; (println "loading proj.widgets")

(miraj/defcomponent snippet :html proj-snippet
  "snippet component"

  (:codom
   (h/div
   ;; (h/template {:is "dom-if" :if "{{clj}}"}
   (h/pre :.prettyprint.lang-clj {:hidden (p/bind!! :!clj)} (p/slot {:select ".clj"}))
   (h/pre :.prettyprint.lang-html {:hidden (p/bind!! :!html)} (p/slot {:select ".html"}))
   ;; (h/template {:is "dom-if" :if "{{html}}"}
   ;; (h/pre :.prettyprint.lang-html {:hidden (p/bind!! :!language-html)} (p/slot {:select "code"}))
               ))

;))

  {:polymer/properties {:clj {:value false :type Boolean}
                        :html {:value false :type Boolean}}})

  ;; miraj.polymer.protocols/Lifecycle
  ;; (ready [] (.log js/console "READY greeting")))

(miraj/defcomponent greeting :html proj-greeting
  "my-greeting component"

  (:codom
   (h/h1 :.page-title {:tabindex "-1"} (p/bind!! :greeting))
   (h/label {:for "greeting-input"} "Update text to change the greeting.")
   ;; Listens for "input" event and sets greeting to <input>.value
   (h/input :#greeting-input {:value (p/bind!! :input->greeting)}))

  {:polymer/properties {:greeting ^String{:value "Welcome!"
                                          :type String
                                          :notify :true}}}
  miraj.polymer.protocols/Lifecycle
  (ready [] (.log js/console "READY greeting")))

(miraj/defcomponent list :html proj-list
  "my-list component"

  (:codom
   (h/ul
    ;; <template is="dom-repeat" items="{{items}}">
    ;; (p/repeat {:items (p/bind!! :items)}
    (h/template {:is "dom-repeat" :items (p/bind!! :items)}
                (h/li (h/span (p/bind!! :item))))))

  {:polymer/properties {:items ^Vector{:notify :true}}}

  miraj.polymer.protocols/Lifecycle
  (ready [] (this-as this (list/ready this))))

(miraj/defcomponent device-card :html proj-device-card
  "proj devices component"
  (:require [miraj.polymer.paper :as paper :refer [button card]]
            [miraj.polymer.iron :as iron :refer [ajax icon]])

  (:codom
   (h/a {:href (str "#/detail/" (p/bind!! :device.di))
         :class "card-container"}
        (paper/card   ;; {:image (p/bind!! :item.imageUrl)}
         (h/div :.card-content
                (h/h2 :.device-name (p/bind!! :device.n))
                ;; (h/div (p/slot) (h/span "foo"))
                (h/div "Types: "
                       (h/template {:is "dom-repeat" :items (p/bind!! :device.rt)}
                                   (h/p {:miraj.style/padding-left "16px"}
                                        (p/bind!! :item))))
                ))))

  {:polymer/properties {:device {:type Map}}
   ;; :ajax-handler (fn [r] (this-as this (devices/ajax this r)))
   }

  miraj.polymer.protocols/Lifecycle
  (ready [] (this-as this (device-card/ready this))))

(miraj/defcomponent devices :html proj-devices
  "proj devices component"
  (:require [miraj.polymer.paper :as paper :refer [button card]]
            [miraj.polymer.iron :as iron :refer [ajax icon]])

  (:codom
   (iron/ajax :?auto {;; :url "/data/recipes.json"
                      :url "/data/devices.json"
                      ;;:url "/data/resources.json"
                      ;;:url "/data/devstrings.json"
                      :handle-as "json"
                      ;; :on-response "ajax-handler"
                      ;; :last-error "ajax-error"
                      :last-response (p/bind!! :devices)})
   (h/div
    (h/h1 :.page-title {:tabindex "-1"} "Devices")
    (h/template {:is "dom-repeat" :items (p/bind!! :devices)}
                (device-card {:device (p/bind!! :item)}) ;;  (h/span "foo"))

                #_(h/a {:href (str "#/detail/" (p/bind!! :item.di))
                        :class "card-container"}
                       (paper/card   ;; {:image (p/bind!! :item.imageUrl)}
                        (h/div :.card-content
                               (h/h2 (p/bind!! :item.n))
                               (h/div "Types: "
                                      (h/template {:is "dom-repeat" :items (p/bind!! :item.rt)}
                                                  (h/p {:miraj.style/padding-left "16px"}
                                                       (p/bind!! :item))))
                               ))))))

  {:polymer/properties {:devices {:type Vector}}
   ;; :ajax-handler (fn [r] (this-as this (devices/ajax this r)))
   }

  miraj.polymer.protocols/Lifecycle
  (ready [] (this-as this (devices/ready this))))

;; (println "loaded proj.widgets")
