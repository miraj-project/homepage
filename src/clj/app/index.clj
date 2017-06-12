(ns index
  ;; (:refer-clojure :exclude [list])
  (:require [miraj.core :as miraj :refer [defcomponent]]
            [miraj.html :as h]
            [miraj.html.x.apple :as apple]
            [miraj.html.x.ms :as ms]
            [miraj.polymer :as p]
            [proj.gadgets :as proj :refer :all]))

(println "loading index")

;(-> *ns* meta)

(def apple-meta
  #::apple{:mobile #::apple{:status-bar-style :black
                            :title "Miraj Starter Kit"}
           :touch #::h{:icons [#::h{:href "images/touch/apple-touch-icon.png"}]}})

(def ms-meta
       #::ms{:tile #::ms{:color "#3372DF"
                         :image "images/touch/ms-touch-icon-144x144-precomposed.png"}})

(def chrome-meta #::h{:web-app-capable true})

(def platforms (merge apple-meta ms-meta chrome-meta))

(miraj/defpage
  "Miraj Project Homepage"
  (:require [miraj.polymer.iron :as iron :refer [pages selector]]
            [miraj.polymer.paper :as paper :refer [drawer-panel
                                                   icon-button
                                                   item
                                                   card
                                                   material
                                                   menu
                                                   scroll-header-panel
                                                   toast
                                                   toolbar]])

  (:js ["scripts/app.js"
        "https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js?lang=clj"])

  #::h{:title "Miraj Home Page"
       :description "Miraj Home Page"
       :charset "utf-8"
       :viewport {::h/width :device-width
                  ::h/scale {::h/min 0.5 ::h/max 2 ::h/initial 1}
                  ::h/user-scalable true}
       :platform platforms

       ::h/icons [{::h/href "images/touch/chrome-touch-icon-192x192.png"
                   ::h/sizes #{[192 192]}
                   ::h/type "icon"}]

       ::h/pragma {::h/x-ua-compatible "IE=edge,chrome=1"
                   ::h/default-style "main-style"
                   ::h/pics-label "pics label"}}

  (:body :?unresolved
   (h/span :#browser-sync-binding)
   (h/template {:is "dom-bind" :id "app"}
               (paper/drawer-panel
    :#paperDrawerPanel
    ;; Drawer Scroll Header Panel
    (paper/scroll-header-panel :?drawer?fixed
        ;; Drawer Toolbar
        (paper/toolbar :#drawerToolbar (h/span :.menu-name "Menu"))

        ;; Drawer Content
        (paper/menu :.app-menu {:attr-for-selected "data-route"
                                :selected (p/bind! :route)}
          (h/a {:data-route "home" :href (p/bind!! :baseUrl)}
            (iron/icon {:icon "home"})
            (h/span "Home"))

          (h/a {:data-route "html5" :href (str (p/bind!! :baseUrl)"html5")}
            (iron/icon {:icon "image:camera"})
            (h/span "HTML5"))

          (h/a {:data-route "pages" :href (str (p/bind!! :baseUrl)"pages")}
            (iron/icon {:icon "content-copy"})
            (h/span "Pages"))

          (h/a {:data-route "polymer" :href (str (p/bind!! :baseUrl)"polymer")}
            (iron/icon {:icon "polymer"})
            (h/span "Polymer"))

          (h/a {:data-route "components" :href (str (p/bind!! :baseUrl)"components")}
            (iron/icon {:icon "picture-in-picture"})
            (h/span "Custom Components"))

          (h/a {:data-route "libraries" :href (str (p/bind!! :baseUrl)"libraries")}
            (iron/icon {:icon "av:library-books"})
            (h/span "Component Libraries"))

          (h/a {:data-route "dom" :href (str (p/bind!! :baseUrl)"dom")}
            (iron/icon {:icon "code"})
            (h/span "Dom and Codom"))

          (h/a {:data-route "styling" :href (str (p/bind!! :baseUrl)"styling")}
            (iron/icon {:icon "line-style"})
            (h/span "Styling"))

          (h/a {:data-route "boot-miraj" :href (str (p/bind!! :baseUrl)"boot-miraj")}
            (iron/icon {:icon "build"})
            (h/span "boot-miraj"))

          (h/a {:data-route "workflow" :href (str (p/bind!! :baseUrl) "workflow")}
            (iron/icon {:icon "refresh"})
            (h/span "Workflow"))

          (h/a {:data-route "roadmap" :href (str (p/bind!! :baseUrl) "roadmap")}
            (iron/icon {:icon "maps:my-location"})
            (h/span "roadmap"))
          ))

      ;; Main Area
      (paper/scroll-header-panel :?main#headerPanelMain?condenses?keep-condensed-header
        ;; Main Toolbar
        (paper/toolbar :#mainToolbar.tall
          (paper/icon-button :#paperToggle {:icon "menu"} :?paper-drawer-toggle)
          (h/span :.space)

          ;; Toolbar icons
          (paper/icon-button {:icon "refresh"})
          (paper/icon-button {:icon "search"})

          ;; Application name
          (h/div :.middle.middle-container
            (h/div :.app-name "Miraj"))

          ;; Application sub title
          (h/div :.bottom.bottom-container
            (h/div :.bottom-title "The future of web app development, today")))

        ;; Main Content
        (h/div :.content

           (iron/pages {:attr-for-selected "data-route" :selected (p/bind!! :route)}
            (h/section {:data-route "home" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"})
              (paper/material {:elevation "1"}
                (h/p "The goal of the Miraj Project is to create a
                pure Clojure, 100% functional programming model for
                web application development, including first-class
                support for defining and using " (h/a
                {:href "https://www.webcomponents.org/"} "Web
                Components") " (" (h/a
                {:href "https://www.polymer-project.org/"} "Polymer") "
                only for trhis version).")

                (h/p "That means two things: support for programming
                each of the three web languages (HTML, Javascript,
                CSS) in Clojure, and shielding the programmer from
                low-level details like configuring the <head> element
                of a webpage, configuring custom elements, etc."))
                ;; (proj/greeting)
                ;; (h/p :.subhead "You now have:")
                ;; (proj/list))

              (paper/material {:elevation "1"}
                (h/p "Miraj treats the three \"native\" languages of
                web development (HTML, Javascript, CSS) as a kind of
                web \"assembly\" language.  The programmer defines
                pages and components in Clojure (including
                Clojurescript), which Miraj translates into HTML,
                Javascript, and CSS.")

                (h/p "Clojurescript already eliminates the need to
                program in Javascript; Miraj does the same for
                HTML.  (A genuine Clojure face for CSS programming
                remains a future project).")

                (h/p "Providing Clojure functions for HTML elements is
                relatively trivial.  Miraj also provides macros that
                make page and component definitions look similar to
                the " (proj/snippet :?clj.inline (h/code :.clj "deftype")) "
                and " (proj/snippet :?clj.inline (h/code :.clj "defrecord")) "
                constructions of Clojure.")

                (h/p "Things get a little more complicated when you
                add web components.  Miraj allows the programmer to
                define and use Polymer-based components in idiomatic
                Clojure, without having to worry about the directory
                structures, file names, and href values required to
                make the generated HTML, Javascript and CSS files work
                together."))

              (paper/material {:elevation "1"}
                (h/p "Miraj also makes it very easy to define and
                share component libraries.  Multiple components may be
                defined across multiple namespaces; a "
                     (proj/snippet :?clj.inline (h/code :.clj "deflibrary"))
                " macro then assembles any combination of components
                into a library namespace, which is independent of the
                defining namespaces.  Miraj can automatically generate
                a demo page for previewing/testing components under
                development.")

                (h/p "Components can also be easily defined as one-off
                elements for use in a single page.  Both page and
                components can be defined in the same project.")))


            (h/section {:data-route "html5" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "HTML5")
              (h/p "Assuming " (proj/snippet :?clj.inline (h/code :.clj "miraj.html")) "
              is aliased to " (proj/snippet :?clj.inline (h/code :.clj "h")) ":")

              ;; test:
               ;; (h/pre :.prettyprint.lang-clj
               ;;                "(let [x 9] (h/div (h/span \"Hello World\")))")

              (paper/material {:elevation "1"}
                (h/h3 "Basics")
                (h/ul
                 (h/li (h/div "One function per HTML element:")
                       (proj/snippet :?clj
                        (h/code :.clj "(h/div (h/span \"Hello World\"))"))
                       (proj/snippet :?html
                        (h/code :.html "<div><span>Hello World</span></div>")))

                 (h/li (h/div "test")
                       (proj/snippet :?clj?html
                        (h/code :.clj "(h/div (h/span \"Hello World\"))")
                        (h/code :.html "<div><span>Hello World</span></div>")))

                 (h/li (h/div "Attributes are passed as a keyword map:")
                       (proj/snippet :?clj?html
                        (h/code :.clj "(h/span {:class \"foo\"} \"Hello World\")")
                        (h/code :.html "<span class=\"foo\">Hello World</span>")))))

              (paper/material {:elevation "1"}
                (h/h3 "Sugar")
                (h/p "For id, class, and boolean attributes:")
                (h/ul
                 (h/li (proj/snippet :?clj?html
                        (h/code :.clj "(h/span :#foo \"Hello World\")")
                        (h/code :.html "<span id=\"foo\">Hello World</span>")))
                 (h/li (proj/snippet :?clj?html
                        (h/code :.clj "(h/span :.bar.baz \"Hello World\")")
                        (h/code :.html "<span class=\"bar baz\">Hello World</span>")))
                 (h/li (proj/snippet :?clj?html
                        (h/code :.clj "(h/span :?centered?horizontal \"Hello World\")")
                        (h/code :.html "<span centered horizontal>Hello World</span>")))
                 (h/li (proj/snippet :?clj?html
                        (h/code :.clj "(h/span :#foo.bar?centered \"Hello World\")")
                        (h/code :.html "<span id=\"foo\" class=\"bar\" centered>Hello World</span>"))))
                (h/p "For inline styles, use the "
                     (proj/snippet :?clj.inline (h/code :.clj "miraj.style"))
                     " namespace:")
                (h/ul
                 (h/li (proj/snippet :?clj?html
                        (h/code :.clj "(h/span {:miraj.style/color \"blue\"} \"Hello World\")")
                        (h/code :.html "<span style=\"color:blue;\">Hello World</span>"))))

                (h/p "Pseudo-classes and -elements are supported:")

                (h/ul
                 (h/li
                  (h/span "Hover: " (h/span :#foobar {:miraj.style/hover {:color "red"}} " Hello World"))
                  (proj/snippet :?clj?html
                   (h/code :.clj "(h/span :#foo {:miraj.style/hover {:color \"red\"} \"Hello World\")")
                   (h/code :.html "<span id=\"foo\">Hello World</span><style>#foo:hover {color:red;}</style>")))

                 (h/li (h/span "using :before pseudo element:  " (h/span :#beforeex {:miraj.style/before {:content "Howdy, "}} "World"))
                       (proj/snippet :?clj?html
                        (h/code :.clj "(h/span :#foo {:miraj.style/before {:content \"Howdy, \"} \"World\")")
                        (h/code :.html "<span id=\"foo\">World</span><style>#foo:before {content: \"Howdy, \"}</style>")))))

              (paper/material {:elevation "1"}
                (h/h3 "Styling")))


                         ;; ::s/before {:content "foo
                         ;; ::s/after {:content "bar"}
                         ;; ::s/hover {:color "green!important"
                         ;;            :background-color "red"}}


            (h/section {:data-route "pages" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "Pages")
                (paper/material {:elevation "1"}
                  (h/p "To define an index.html page:")
                  (proj/snippet :?clj?html
                    (h/pre :.clj "(ns index
  (:require [miraj.core :as miraj :refer [defpage]]
            [miraj.html :as h] ...))
...
(defpage
  \"PAGE DOCSTRING\"
  (:require [miraj.polymer.paper :as paper :refer [card]])
  ;; HTML meta-data passed as a (spec-validated) Clojure map:
  #::h{:title \"Page Title\"
       :description \"Page description\"
       :charset \"utf-8\"
       :viewport {::h/width :device-width
                  ::h/scale {::h/min 0.5 ::h/max 2 ::h/initial 1}
                  ::h/user-scalable true}}
  (:body :?unresolved
    (h/h1 \"Hello, World Demo\")
    (paper/card (h/div \"Hi, Card!\"))))")
   (h/pre :.html "<!doctype html>
<!-- generated index.html -->
<html>
    <head>
        <meta content=\"utf-8\" name=\"charset\">
        <title>Page Title</title>
        <meta content=\"Page description\" name=\"description\">
        <meta content=\"width=device-width, minimum-scale=0.5, maximum-scale=2, initial-scale=1, user-scalable=yes\" name=\"viewport\">
        <link rel=\"import\" href=\"/miraj/polymer/assets/paper-card/paper-card.html\">
    </head>
    <body unresolved>
        <h1>Hello, World Demo</h1>
        <paper-card>
            <div>Hi, Card!</div>
        </paper-card>
    </body>
</html>")
)))

            (h/section {:data-route "polymer" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "Polymer")
                (paper/material {:elevation "1"}
                  (h/p "...examples..")))

            (h/section {:data-route "components" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "Custom Components")
                (paper/material {:elevation "1"}
                  (h/p "This is the components section")))

            (h/section {:data-route "libraries" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "Component Libraries")
                (paper/material {:elevation "1"}
                  (h/p "This is the component libraries section")))

            ;; (h/section {:data-route "user-info" :tabindex "-1"}
            ;;     (h/h1 :.page-title {:tabindex "-1"} "User: " (p/bind!! :params.name))
            ;;   (paper/material {:elevation "-1"}
            ;;     (h/div "This is " (p/bind!! :params.name) "'s section")))

            (h/section {:data-route "dom" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "Dom")
                (paper/material {:elevation "1"}
                  (h/p "This is the dom section")))

            (h/section {:data-route "styling" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "Styling")
                (paper/material {:elevation "1"}
                  (h/p "foo")))

            (h/section {:data-route "boot-miraj" :tabindex "-1"}
              (h/h1 :.page-title {:tabindex "-1"} "boot-miraj")
                (paper/material {:elevation "1"}
                  (h/p "foo")))

            (h/section {:data-route "workflow" :tabindex "-1"}
              (paper/material {:elevation "1"}
                (h/h1 :.page-title {:tabindex "-1"} "Workflow")
                (h/p "This is the workflow section")))

            (h/section {:data-route "roadmap" :tabindex "-1"}
              (paper/material {:elevation "1"}
                (h/h1 :.page-title {:tabindex "-1"} "Status &amp; Roadmap")
                (h/p "This is the roadmap section")))

            ))))
               (paper/toast :#toast
      (h/span :.toast-hide-button {:role "button"
                                   :tabindex "0"
                                   :onclick "app.$.toast.hide()"}
              "Ok")))

   ;; (h/div :#app
   ;;        (shell))
   ;; (widgets/greeting)
   ;; (widgets/list)
   ))

(miraj/defpage foobar
  "PAGE DOCSTRING"
  (:require [miraj.polymer.paper :as paper :refer [card]])
  ;; HTML meta-data passed as a (spec-validated) Clojure map:
  #::h{:title "Page Title"
       :description "Page description"
       :charset "utf-8"
       :viewport {::h/width :device-width
       ::h/scale {::h/min 0.5 ::h/max 2 ::h/initial 1}
                  ::h/user-scalable true}}
  (:body :?unresolved
    (h/h1 "Hello, World Demo")
    (paper/card (h/div "Hi, Card!"))))


(println "loaded index")
