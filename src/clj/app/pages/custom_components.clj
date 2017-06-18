(ns pages.custom-components
  (:require [miraj.html :as h]
            [miraj.polymer.paper :as paper :refer [material]]
            [proj.gadgets :as proj :refer :all]
            [lib.msg :as msg :refer :all]))

(def page
  (h/section {:data-route "components" :tabindex "-1"}
             (h/h1 :.page-title "Custom Components")
             (paper/material {:elevation "1"}
               (h/p "Polymer custom components typically combine the following in a single HTML file:")
               (h/ul
                 (h/li "A blob of HTML to define the component's local DOM")
                 (h/li "A dash of CSS to style the component")
                 (h/li "Some Javascript to define the properties and methods of the component, and to register it at runtime")
                 (h/li "A collection of <link> elements to import external dependencies"))

               (h/p "Note that all of this gets imported (loaded) in
               one stroke, so the component will be registered as soon
               as the HTML file is loaded.  But this is optional; the
               HTML and CSS can be loaded separately from the
               Javascript that registers the component.  This is the
               strategy Miraj adopts.  The Miraj source code defining
               a component is divided as follows:")

               (h/ul
                 (h/li "A Clojure file contains the defcomponent definition of the component's DOM, properties, and methods")
                 (h/li "A Clojurescript " (h/i "delegate") " namespaces contains functional implementations of the component's methods")
                 (h/li "The CSS for the component goes in a CSS file named according to the component")
                 (h/li "An EDN file specifies external dependencies"))

               (h/p "These files must follow a strict naming
               convention (this restriction may be relaxed in a future
               release): the namespaace of the component is converted
               into a path by interpreting each segment of the
               namespace as a directory, and the name of the
               component, suffixed by the appropriate extension, is
               interpreted as the filename.  For example, the definition
               of component " (h/code "foo.bar/sweet") " will use the following files: ")

               (proj/snippet :?clj (h/code :.clj "
foo
├── bar.clj          ;; contains defcomponent sweet
├── bar              ;; organizes the optional implementation files
│   ├── sweet.cljs   ;; delegate namespace
│   ├── sweet.css    ;; styling specific to this component
│   └── sweet.edn    ;; external resources"
))

               (h/p "Compiling and linking this component results in the following output:")
               (proj/snippet :?clj (h/code :.clj "
foo
├── bar
│   ├── sweet
│   │   └── core.cljs  ;; generated code: registers component, delegates method calls to bar.sweet
│   ├── sweet.cljs     ;; delegate ns
│   ├── sweet.html     ;; templated local DOM plus CSS"
))

               (h/p "To use this component, a web page must import
               sweet.html (via <link rel=\"import\"...>), compile the
               Clojurescript (along with any other clojurescript used
               by the page) to main.js, and load the resulting
               Javascript file.  Miraj handles all this
               automatically, so long as you follow the conventions.")

              (h/h3 "defcomponent")
              (h/a "The "
                   (proj/snippet :?clj.inline (h/code :.clj "miraj.core/defcomponent"))
                   " macro has the following structure:")

                   (proj/snippet :?clj (h/code :.clj "
(defcomponent sweet :html acme-sweet  ;; html tag can be anything, must have at least one dash '-'
  \"clojure docstring\"
  (:require  ...  just like :require for clojure.core/ns ...)
  (:codom ... html code here ...)
  {:polymer/properties  ...  }  ;; polymer prototype definition as clojure map
  ;; callbacks, modeled by protocols:
  miraj.polymer.protocols/Lifecycle
  (created [] (sweet/created))   ;; delegate to delegate ns
  miraj.html.protocols/Mouse
  (click [e] (this-as this (sweet/click this e))))
"))

                   (msg/important "Within defcomponent, Miraj aliases
              the Clojurescript delegate namespace to the component
              name; in this example, 'sweet' is the alias for
              foo.bar.sweet (foo/bar/sweet.cljs)")

                   (h/p "The prototype definition map is a little complex.  Here is an example; see the hello-world demos for more.  Polymer documentation at "
                        (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/properties"}
                             "Declared properties")
                        " and "
                        (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/instance-methods"}
                             "Instance methods") ".")

                   (proj/snippet :?clj (h/code :.clj "
  ;; properties defined in :polymer/properties are exposed as part of the
  ;; public interface of the component
  {:polymer/properties {:greeting ^String{:value \"hello\"
                                          :type String
                                          :observer (fn [new old] (sweet/observe-greeting new old))}}

   ;; static html attributes on host (Polymer hostAttributes property)
   ;; these will cause html attr vals to be set at create time
   ;; see https://www.polymer-project.org/1.0/docs/devguide/registering-elements#host-attributes
   :polymer/static {:string-attr1 \"attr1\"
                    :boolean-attr2 true
                    :foo \"Hello\"
                    :tabindex 0}

   ;; complex observers take (keyword) properties as params
   ;; this will be fired whenever either property changes
   ;; https://www.polymer-project.org/1.0/docs/devguide/observers#complex-observers
   :greeting-flavor-observer (fn [:greeting :flavor]
                               ;; delegate, passing the args as syms
                               (sweet/greeting-flavor-observer greeting flavor))

   ;; local properties - we can put them in the prototype, or in a cljs namespace
   ;; for polymer data binding, properties must be public (defined in :polymer/properties)
   :name {:last \"Smith\"
          :first \"John\"}

   ;; \"instance\" methods (https://www.polymer-project.org/1.0/docs/devguide/instance-methods)
   ;; with javascript, instance methods go in the component's prototype
   ;; with clojurescript, we don't need this - just use functions in the delegate namespace
   :foo-bar (fn [evt] (this-as this (sweet/foo-bar this evt)))
   }"))

              (h/p "See the "
                 (h/a {:href "https://github.com/miraj-project/demos/tree/master/hello-world/acme-widgets"}
                      "acme-widgets")
                 " demo for detailed examples of defining custom components.")
                   )))

