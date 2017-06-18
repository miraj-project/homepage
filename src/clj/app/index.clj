(ns index
  ;; (:refer-clojure :exclude [list])
  (:require [clojure.string :as str]
            [miraj.core :as miraj :refer [defcomponent]]
            [miraj.html :as h]
            [miraj.html.x.apple :as apple]
            [miraj.html.x.ms :as ms]
            [miraj.polymer :as p]
            [pages.custom-components]))

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

;; we can't use entity chars containing & like &mdash; since the & will be escaped
(def figure-dash \u2012)
(def en-dash "\u2013")
(def em-dash "\u2014")

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
                                                   toolbar]]
            [lib.msg :as msg :refer :all]
            [proj.gadgets :as proj :refer :all])

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

          (h/a {:data-route "miraj" :href (str (p/bind!! :baseUrl)"miraj")}
            (iron/icon {:icon "content-copy"})
            (h/span "Miraj"))

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
            (h/span "Status & Roadmap"))
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
              (h/h1 :.page-title)
              (paper/material {:elevation "1"}
                (h/p "The goal of the Miraj Project is to create a
                pure Clojure, 100% functional programming model for
                web application development, including first-class
                support for defining and using " (h/a
                {:href "https://www.webcomponents.org/"} "Web
                Components") " (" (h/a
                {:href "https://www.polymer-project.org/"} "Polymer") "
                only for this version).")

                (h/p "Miraj is largely motivated by the following observations:")
                (h/ul
                 (h/li "The three languages of the web (HTML,
                Javascript, CSS) together serve as a kind of web
                \"assembly\" language.  Nobody wants to program in
                assembly, let alone three different assembly
                languages.")

                 (h/li "HTML element tags are (co-)functions.  Like
                 functions, they are applied to arguments (attributes
                 and child elements), and they always do the same
                 thing given the same input (and evaluation
                 environment).  The only difference is that functions
                 result in values, and HTML element co-functions
                 result in behavioral side-effects.")

                 (h/li (h/div "An HTML page is analogous to a Clojure program
                 of one function, main.  In particular, the "
                       (proj/snippet :?html.inline (h/code :.html "<link>"))
                       " and "
                       (proj/snippet :?html.inline (h/code :.html "<script>"))
                       " elements in the "
                       (proj/snippet :?html.inline (h/code :.html "<head>"))
                       " element are analogous to the "
                       (proj/snippet :?clj.inline (h/code :.clj ":require"))
                       " and "
                       (proj/snippet :?clj.inline (h/code :.clj ":import"))
                       " directives of Clojure's "
                       (proj/snippet :?clj.inline (h/code :.clj "ns"))
                       " macro: they tell the runtime to find, fetch, and load the referenced resources."))

                 )

                (h/p "Miraj eliminates mixed-language programming,
                allowing the programmer to define pages and
                components in Clojure.  Miraj compiles this Clojure
                code into HTML, Javascript, and CSS."))

              (paper/material {:elevation "1"}

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
                components can be defined in the same project."))

              (paper/material {:elevation "1"}
                (h/p "Miraj is composed of several layers:")
                (h/ul
                 (h/li "The base layer is "
                     (h/a {:href "https://github.com/miraj-project/co-dom"} "miraj.co-dom") ".  This library
                     is derived from version 0.8.0 of "
                     (h/a
                     {:href "https://github.com/clojure/data.xml"} "data.xml")
                     ". "
                     "Miraj uses miraj.co-dom to build an XML tree
                     representation of the HTML, which it then
                     serializes to an HTML5 string.  For example,  "
                     (proj/snippet :?html.inline (h/code :.html "<span>Hello, world!</span>"))
                     " is represented by "
                     (proj/snippet :?clj.inline (h/code :.clj "#miraj.co_dom.Element{:tag :span, :attrs {}, :content (\"Hello, world!\")}"))
                     ".  Normally, the programmer will not need to use the co-dom library directly.")

                 (h/li "The " (h/a {:href "html5"} "miraj.html") " library
                 is a wrapper around " (h/code "miraj.co-dom") "; it
                 provides one Clojure function per HTML5 element
                 tag.  For example, in a repl:"
                       (proj/snippet :?clj (h/code :.clj "index> (def howdy (h/span \"Hello, world!\"))
#'index/howdy
index> howdy
#miraj.co_dom.Element{:tag :span, :attrs {}, :content (\"Hello, world!\")}") )
                       "This serializes
                       to " (proj/snippet :?html.inline
                                          (h/code :.html "<span>Hello, world!</span>"))
                       ".")

                 (h/li "The " (h/a {:href "miraj"} "miraj.core") " library
                 adds support for:"
                       (h/ul
                        (h/li "A " (proj/snippet :?clj.inline (h/code :.clj "defpage")) "
                        macro for defining HTML5 pages.")
                        (h/li "A " (proj/snippet :?clj.inline (h/code :.clj "defcomponent")) "
                        macro for defining (Polymer) web components.")
                        (h/li "A " (proj/snippet :?clj.inline (h/code :.clj "deflibrary")) "
                        macro for defining component libraries.")
                        (h/li "Functions for compiling and linking (i.e. serializing) Miraj structures.")))

                 (h/li "A collection of " (h/a
                 {:href "polymer"} "Polymer libraries") " provides
                 support for Polymer components.")

                 (h/li (h/a {:href "boot-miraj"} "boot-miraj") ", a " (h/a
                 {:href "https://github.com/boot-clj/boot"} "boot") "
                 task library for Miraj programming.")

                ))

              (paper/material {:elevation "1"}
                (h/p "NOTE: this website was build using Miraj.  The source code is available at:"
                     (h/a {:href "https://github.com/miraj-project/homepage"}
                          "miraj-project/homepage")
                     "."))

              )

            (h/section {:data-route "html5" :tabindex "-1"}
              (h/h1 :.page-title "HTML5")
              (h/p "The " (h/a
              {:href "https://github.com/miraj-project/html"} "miraj.html") "
              library wraps the lower-level " (h/a
              {:href "https://github.com/miraj-project/co-dom"} "miraj.co-dom") "
              library, providing one function per HTML5 element, as
              well as some additional goodies.")
              (h/p "Assuming " (proj/snippet :?clj.inline (h/code :.clj "miraj.html")) "
              is aliased to " (proj/snippet :?clj.inline (h/code :.clj "h")) ":")

              ;; test:
               ;; (h/pre :.prettyprint.lang-clj
               ;;                "(let [x 9] (h/div (h/span \"Hello World\")))")

              (paper/material {:elevation "1"}
                (h/h3 "Elements")
                (h/ul
                 (h/li (h/div "One function per HTML element:  "
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/div (h/span \"Hello World\"))")
                        (h/code :.html "<div><span>Hello World</span></div>"))))

                 (h/li (h/div "HTML5 "
                              (h/a {:href "https://www.w3.org/TR/html51/syntax.html#void-elements"}
                                   "void elements")
                              " cannot have any content; they also
                              cannot be \"self-closing\"; they may
                              only have a start tag with no '/'.
                              Miraj understands void elements:  "
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/br)")
                        (h/code :.html "<br>"))))

                 (h/li (h/div "HTML5 empty elements must not be
                 self-closing; they must have a close tag.  Miraj
                 understands empty elements:")
                       (proj/snippet :?clj?html.inline
                                     (h/code :.clj "(h/script {:src \"foo.js\"})")
                                     (h/code :.html "<script src=\"foo.js\"></script>")))

                 (h/li (h/div "It's all functions; compose at will:")
                     (proj/snippet :?clj?html.inline
                         (h/code :.clj "(let [stooges [\"Larry\" \"Moe\" \"Curly\"]]
  (h/ul (for [stooge stooges]
    (h/li stooge))))")
                                     (h/code :.html "<ul><li>Larry</li>
  <li>Moe</li>
  <li>Curly</li></ul>"))
                     (let [stooges ["Larry" "Moe" "Curly"]]
                       (h/ul (for [stooge stooges]
                               (h/li stooge)))))
                 ))

              (paper/material {:elevation "1"}
                (h/h3 "Text Nodes")
                (h/ul
                 (h/li (h/div "Escaping of <angle brackets> & ampersands is handled automatically:")
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span \"Hello & Goodbye, <i>World</i>\")")
                        (h/code :.html "<span>Hello &amp; Goodbye, &lt;i&gt;World&lt;/i&gt;</span>"))
                       (h/div "Displays as: " (proj/snippet :?html.inline
                                               (h/code :.html "Hello & Goodbye, <i>World</i>"))))

                 (h/li (h/div "Embedded double quotes must be escaped:")
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span \"Hello \\\"World\\\" ('Howdy')\")")
                        (h/code :.html "<span>Hello \"World\" ('Howdy')</span>")))
 
                 (h/li (h/div "Character entity references like &euro;
                 require special handling, since & is automatically
                 escaped.  Use the Unicode literal (for example,
                 \\u20AC for the Euro sign, \u20AC). You can embed
                 character literals directly, or you can use ordinary
                 Clojure definitions or bindings to get names:")
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(let [euro-sign \"\\u20AC\"] (h/span \"Hello, Euro: \" euro-sign))")
                        (h/code :.html (str "<span>Hello, Euro: \u20AC</span>")))
                       (h/div (h/span "Displays as: "
                                      (proj/snippet :?html.inline (h/code :.html "Hello, Euro: \u20AC")))))

                 (h/li (h/div (h/b "WARNING: ") "if you are using Polymer, you must escape opening double braces " (h/code "{\uFEFF{") " and brackets " (h/code "[\uFEFF[") " if you want to display them in a string, since Polymer treats these as special \"binding annotations\" (see "
                              (h/a {:href "polymer"} "polymer") " for
                              more info).  I.e. if you put something
                              inside double braces or brackets, it will be interpreted as a property and will be displayed as null if it has no value:")
                       (h/span (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span \"Hello {\uFEFF{World}}\")")
                        (h/code :.html "<span>Hello {\uFEFF{World}}</span>"))
                        " displays as: "
                        (proj/snippet :?html.inline
                                   (h/code :.html "Hello {{World}}")))
                       (h/div "Use the Unicode character \\uFEFF,
                              'ZERO WIDTH NO-BREAK SPACE', to force
                              display of the delimiters without
                              Polymer interpretation:")
                       (h/span (proj/snippet :?clj?html.inline
                                             (h/code :.clj "(h/span \"Hello {\\uFEFF{World}}\")")
                                             (h/code :.html "<span>Hello {\\uFEFF{World}}</span>"))
                               " displays as: "
                               (proj/snippet :?html.inline (h/code :.html "Hello {\uFEFF{World}}"))))

                 (h/li (h/div "You can split text nodes:")
                       (proj/snippet :?clj?html.inline
                                     (h/code :.clj "(h/span \"Lorem ipsum dolor sit amet,\"
\" consectetur adipiscing elit.\")")
                                     (h/code :.html "<span>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</span>")))

                 (h/li (h/div "You can use Clojure expressions:")
                       (proj/snippet :?clj?html.inline
                                     (h/code :.clj "(h/span \"Lorem ipsum\" (+ 2 3))")
                                     (h/code :.html "<span>Lorem ipsum 6</span>"))
                       (proj/snippet :?clj?html.inline
                                     (h/code :.clj "(h/span \"Lorem \" (clojure.string/join \", \" (repeat 3 \"ipsum\")))")
                                     (h/code :.html "<span>Lorem ipsum, ipsum, ipsum</span>")))

                 (h/li (h/div "Inline elements are " (h/i "easy") "!")
                       (proj/snippet :?clj?html.inline
                                     (h/code :.clj "(h/span \"Inline elemeents are \" (h/i \"easy\") \"!\")")
                                     (h/code :.html "<span>Inline elements are <i>easy</i>!</span>")))
))

              (paper/material {:elevation "1"}
                (h/h3 "Attributes")
                (h/ul
                 (h/li (h/div "Attributes are passed as a keyword map:")
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span {:class \"foo\"} \"Hello World\")")
                        (h/code :.html "<span class=\"foo\">Hello World</span>")))

                 (h/li (h/div "With a few exceptions, clojure
                 attribute values go through normal Clojure evaluation
                 and then are serialized as strings. You can use
                 expressions as attribute values:")
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span {:foo (* 2 3)})")
                        (h/code :.html "<span foo=\"6\"></span>")))

                 (h/li (h/div "BigInt and BigDecimal end up looking like Int and Decimal:")
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span {:foo 9N})")
                        (h/code :.html "<span foo=\"9\"></span>"))))
                (h/p "See " (h/a {:href "https://github.com/miraj-project/co-dom"} "miraj.co-dom")
                     " for more examples.")
                 )

              (paper/material {:elevation "1"}
                (h/h3 "Sugar")
                (h/p "For id, class, and boolean attributes:")
                (h/ul
                 (h/li (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span :#foo \"Hello World\")")
                        (h/code :.html "<span id=\"foo\">Hello World</span>")))
                 (h/li (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span :.bar.baz \"Hello World\")")
                        (h/code :.html "<span class=\"bar baz\">Hello World</span>")))
                 (h/li (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span :?centered?horizontal \"Hello World\")")
                        (h/code :.html "<span centered horizontal>Hello World</span>")))
                 (h/li (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span :#foo.bar?centered \"Hello World\")")
                        (h/code :.html "<span id=\"foo\" class=\"bar\" centered>Hello World</span>"))))
                (h/p "For inline styles, use the "
                     (proj/snippet :?clj.inline (h/code :.clj "miraj.style"))
                     " namespace:")
                (h/ul
                 (h/li (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span {:miraj.style/color \"blue\"} \"Hello World\")")
                        (h/code :.html "<span style=\"color:blue;\">Hello World</span>"))))

                (h/p "Pseudo-classes and -elements are supported:")

                (h/ul
                 (h/li
                  (h/span "Hover: " (h/span :#foobar {:miraj.style/hover {:color "red"}} " Hello World"))
                  (proj/snippet :?clj?html.inline
                   (h/code :.clj "(h/span :#foo {:miraj.style/hover {:color \"red\"} \"Hello World\")")
                   (h/code :.html "<span id=\"foo\">Hello World</span><style>#foo:hover {color:red;}</style>")))

                 (h/li (h/span "using :before pseudo element:  " (h/span :#beforeex {:miraj.style/before {:content "Howdy, "}} "World"))
                       (proj/snippet :?clj?html.inline
                        (h/code :.clj "(h/span :#foo {:miraj.style/before {:content \"Howdy, \"} \"World\")")
                        (h/code :.html "<span id=\"foo\">World</span><style>#foo:before {content: \"Howdy, \"}</style>")))))

              (paper/material {:elevation "1"}
                (h/h3 "HTML Metadata")
                (h/p "Pass HTML metadata as a Clojure map; Miraj will
                validate the map against Clojure.spec
                specifications (which may be found in "
                     (h/a {:href "https://github.com/miraj-project/html/blob/master/src/main/clj/miraj/html_spec.clj"} "miraj_spec.clj") " and "
                     (h/a {:href "https://github.com/miraj-project/html/tree/master/src/main/clj/miraj/html/x"} "miraj/x/") ", and then transform it into the appropriate elements in <head>.")
                (proj/snippet :?clj?html
                    (h/pre :.clj "#::h{:title \"Page Title\"
     :description \"Page description\"
     :charset \"utf-8\"
     :viewport {::h/width :device-width
                ::h/scale {::h/min 0.5 ::h/max 2 ::h/initial 1}
                ::h/user-scalable true}}")

   (h/pre :.html "<meta content=\"utf-8\" name=\"charset\">
<title>Page Title</title>
<meta content=\"Page description\" name=\"description\">
<meta content=\"width=device-width,
               minimum-scale=0.5, maximum-scale=2, initial-scale=1,
               user-scalable=yes\"
               name=\"viewport\">")
)))


                         ;; ::s/before {:content "foo
                         ;; ::s/after {:content "bar"}
                         ;; ::s/hover {:color "green!important"
                         ;;            :background-color "red"}}


            (h/section {:data-route "miraj" :tabindex "-1"}
              (h/h1 :.page-title "Miraj")
                (paper/material {:elevation "1"}
                (h/h3 "Pages")
                (h/p "See the "
                     (h/a {:href "https://github.com/miraj-project/demos/tree/master/hello-world"}
                          "Hello World") " demos for many examples.")
                (h/p "To define an index.html page,
                use " (proj/snippet :?clj.inline (h/code :.clj "miraj.core/defpage")) ".")

                (proj/snippet :?clj?html
                    (h/pre :.clj "(ns index
  (:require [miraj.core :as miraj :refer [defpage]]
            [miraj.html :as h]))
  (defpage
    \"PAGE DOCSTRING\"
    #::h{:title \"Page Title\"
         :description \"Page description\"
         :charset \"utf-8\"
         :viewport {::h/width :device-width
                    ::h/scale {::h/min 0.5 ::h/max 2 ::h/initial 1}
                    ::h/user-scalable true}}
    (:body :?unresolved
      (h/h1 \"Hello, World Demo\")))")

   (h/pre :.html "<!doctype html>
<!-- generated index.html -->
<html>
    <head>
        <meta content=\"utf-8\" name=\"charset\">
        <title>Page Title</title>
        <meta content=\"Page description\" name=\"description\">
        <meta content=\"width=device-width,
                       minimum-scale=0.5, maximum-scale=2, initial-scale=1,
                       user-scalable=yes\"
                       name=\"viewport\">
        <link rel=\"import\" href=\"/miraj/polymer/assets/paper-card/paper-card.html\">
    </head>
    <body unresolved>
        <h1>Hello, World Demo</h1>
    </body>
</html>")
)))

            (h/section {:data-route "polymer" :tabindex "-1"}
              (h/h1 :.page-title "Polymer")

              (msg/important "Currently Miraj only supports Polymer
              version 1; support for the "
                             (h/a
                             {:href "https://www.polymer-project.org/blog/2017-05-15-time-for-two.html"}
                             "recently-released version 2")
                             " is in development.")

                (paper/material {:elevation "1"}
                  (h/p "Miraj provides a library, " (h/a
                  {:href ""} "miraj.polymer") ", that supports
                  features specific to Polymer.  For example, it
                  supports Polymer bindings, helper elements, and
                  Event protocols."))

                (paper/material {:elevation "1"}
                  (h/h4 "Polymer Component Libraries")
                  (h/p "In addition, Miraj provides a collection of
                  pre-built libraries for the collection of components built by the "
                       (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/feature-overview"} "Polymer Project")
                       ".  These
                  libraries wrap the native Polymer implementations,
                  which can be found at " (h/a
                  {:href "https://www.webcomponents.org/"} "webcomponents.org"))

                  (h/p (h/b "Warning: ") " only the iron and
                  paper libraries are fully up to date; the remaining
                  libraries are outdated, but will soon be upgraded.")

                  (h/ul
                   (h/li (h/a {:href "https://github.com/miraj-project/polymer-iron"} "miraj.polymer.iron") "  \"Basic building blocks for creating an application.\"  ("
                         (h/a {:href "https://www.webcomponents.org/collection/PolymerElements/iron-elements"} "iron-elements") ")")


                   (h/li (h/a {:href "https://github.com/miraj-project/polymer-paper"}
                              "miraj.polymer.paper")
                         "  Material design UI elements. ("
                         (h/a {:href "https://www.webcomponents.org/collection/PolymerElements/paper-elements"} "paper-elements")
                              ")")

                   ;; (h/li (h/a {:href ""} "miraj.polymer.bluetooth"))
                   (h/li (h/a {:href "https://github.com/miraj-project/polymer-gold"}
                              "miraj.polymer.gold") "  \"Elements built for e-commerce-specific use-cases, like checkout flows.\" (" (h/a {:href "https://www.webcomponents.org/collection/PolymerElements/gold-elements"}
                                                                                                                                    "gold-elements") ")")
                   (h/li (h/a {:href ""} "miraj.polymer.google"))
                   (h/li (h/a {:href ""} "miraj.polymer.layout"))
                   (h/li (h/a {:href ""} "miraj.polymer.molecules"))
                   (h/li (h/a {:href ""} "miraj.polymer.neon"))
                   (h/li (h/a {:href ""} "miraj.polymer.platinum"))
                   ))

                (paper/material {:elevation "1"}
                  (h/h4 "Polymer Assets")
                  (h/p "The assets that implement Polymer components are package in "
                       (h/code "miraj.polymer.assets")
                       "; this library contains everything you would
                       get if you installed using bower, packaged as a
                       jarfile so the assets become available. via the
                       classpath.  Each of the "
                       (h/code "miraj.polymer.*") " libraries
                       has a dependency on this library, so the user
                       never needs to import it directly.")

                  (h/p "To serve your component-based application
statically, or using a non-Java server, you must copy the assets your
app needs to a folder on the server's search path.
The " (h/code "boot-miraj/assetize") " task will copy the contents of
the miraj.polymer.assets jar to the filesystem.  Alternatively, you
can use bower to install the components you need, but the path to them
must be miraj/polymer/assets."))

                (paper/material {:elevation "1"}
                  (h/h4 "Using Polymer Components")

                  (h/p "To use a Polymer component in a webpage,
                  include the library as a dependency in your
                  boot/leiningen project file, and
                  then " (h/code ":require") " it in your Clojure
                  namespace, just like any other
                  library: "
                       (proj/snippet :?clj (h/code :.clj "(ns foo.bar ...) (defpage baz (:require [miraj.polymer.paper :as paper :refer [button card]]) ...)")))

                  (h/p "See the Polymer " (h/a
                  {:href "https://github.com/miraj-project/demos/tree/master/hello-world/polymer"} "hello-world
                  demo") " for more detailed examples.")
 
                  (h/p "Miraj generally follows a simple naming
convention for Polymer components: <foo-bar> becomes
miraj.polymer.foo/bar.  For example, paper-button maps to
miraj.polymer.paper/button.  In some cases, another ns segment is used; for example,
the function for <paper-input-container>
is " (h/code "miraj.polymer.paper.input/container") ".  (Documentation is
incomplete, but the library source code is easily understandable.)")

                  (h/h6 "Data Binding Helper Elements")
                  (h/p "Polymer's " (h/a
                  {:href "https://www.polymer-project.org/1.0/docs/devguide/templates"} "data
                  binding helper elements") " " en-dash "<dom-if>,
                  <dom-repeat>, etc. " en-dash " are implemented
                  in " (h/code "miraj.polymer") ".  Some of the names
                  have been changed to be more consistent with Clojure
                  practice; for example, for <array-selector> we use
                  miraj.polymer/selection.  See the "
                       (h/a {:href "https://github.com/miraj-project/polymer/blob/master/src/main/clj/miraj/polymer.clj"} "source code")
                       " for the complete list."))

                (paper/material {:elevation "1"}
                  (h/h4 "Polymer Bindings")
                  (h/p "Polymer implements a "
                       (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/data-binding"}
                            "data binding") " mechanism that
                            \"[C]onnects data from a custom
                            element (the host element) to a property
                            or attribute of an element in its local
                            DOM (the child or target element).\" Miraj
                            provides idiomatic Clojure support for
                            Polymer bindings.")
                  (h/p "Polymer \"binding annotations\" look like this:")
                  (h/ul
                        (h/li "One-way property bindings use double square brackets: "
                         (proj/snippet :?html.inline
                           (h/code :.html (str "<my-element my-property=\"[\uFEFF[hostProperty]]\">"))))

                        (h/li "One-way " (h/i "attribute") " bindings suffix $ to the attribute name: "
                         (proj/snippet :?html.inline
                           (h/code :.html (str "<my-element my-attribute$=\"[\uFEFF[hostProperty]]\">"))))

                        (h/li "Two-way property bindings use double squiggle braces: "
                         (proj/snippet :?html.inline
                           (h/code :.html (str "<my-element my-property=\"{\uFEFF{hostProperty}}\">"))))

                        (h/li "Two-way " (h/i "attribute") " bindings suffix $ to the attribute name: "
                         (proj/snippet :?html.inline
                           (h/code :.html (str "<my-element my-attribute$=\"{\uFEFF{hostProperty}}\">")))))

                  (h/p "Miraj provides functions for Polymer bindings:")
 
                  (h/ul
                   (h/li (proj/snippet :?clj?html.inline
                    (h/code :.clj (str "(h/span {:foo (miraj.polymer/bind! :bar)}"))
                           (h/code :.html (str "<span foo=\"[\uFEFF[bar]]\"></span>"))))

                   (h/li (proj/snippet :?clj?html.inline
                    (h/code :.clj (str "(h/span {:foo (miraj.polymer/bind-attr! :bar)}"))
                           (h/code :.html (str "<span foo$=\"[\uFEFF[bar]]\"></span>"))))

                   (h/li (proj/snippet :?clj?html.inline
                    (h/code :.clj (str "(h/span {:foo (miraj.polymer/bind!! :bar)}"))
                           (h/code :.html (str "<span foo=\"{\uFEFF{bar}}\"></span>"))))
                   (h/li (proj/snippet :?clj?html.inline
                    (h/code :.clj (str "(h/span {:foo (miraj.polymer/bind-attr!! :bar)}"))
                           (h/code :.html (str "<span foo$=\"{\uFEFF{bar}}\"></span>")))))

                  (h/p "Bindings also work in text nodes, and may be concatentated to text:")
                  (h/ul
                   (h/li (proj/snippet :?clj?html.inline
                    (h/code :.clj
                            (str "(h/span (miraj.polymer/bind! :bar))"))
                    (h/code :.html (str "<span>[\uFEFF[bar]]</span>"))))
                   (h/li (proj/snippet :?clj?html.inline
                    (h/code :.clj
                            (str "(h/span (str \"Hello, \" (miraj.polymer/bind! :name)))"))
                    (h/code :.html (str "<span>Hello, [\uFEFF[name]]</span>"))))
                   (h/li (proj/snippet :?clj?html.inline
                    (h/code :.clj
                            (str "(h/span {:foo (str (miraj.polymer/bind! :baseUrl) \"users/bob\")}"))
                    (h/code :.html (str "<span foo=\"[\uFEFF[baseUrl]]users/bob\"></span>")))))

                  (h/p (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/data-binding#two-way-native"} "Two-way binding to a non-Polymer element") ":  Polymer uses the following special syntax:  "
                       (proj/snippet :?html.inline (h/code :.html "target-prop=\"{\uFEFF{hostProp::target-change-event}}\"")) ".  Miraj uses a more Clojure-like syntax; for example, to listen for 'input' events and  set hostValue to <input>.value: "
                       (proj/snippet :?clj?html
                          (h/code :.clj
                          "(h/input {:value (miraj.polymer/bind!! :input->hostValue)})")
                          (h/code :.html
                          "<input value=\"{\uFEFF{hostValue::input}}\">")))

                (msg/warning "Support for " (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/data-binding#annotated-computed"} "computed bindings") " is not fully implemented.")
                ))

            pages.custom-components/page
  
            (h/section {:data-route "libraries" :tabindex "-1"}
              (h/h1 :.page-title "Component Libraries")
                (paper/material {:elevation "1"}
                  (h/p "See the "
                       (h/a
                       {:href "https://github.com/miraj-project/demos/tree/master/hello-world/acme-widgets"} "acme-widgets") "
                       demo for a detailed example of defining,
                       compiling, and linking a custom component
                       library.")

                  (h/p "You can also wrap 3rd party components into a
                  library, just as Miraj does for Polymer Project
                  components.  The easiest way to proceed is to copy
                  one of the Miraj Polymer libraries and edit.
                  Components are described in edn/webcomponents.edn.
                  Run "
                       (proj/snippet :?clj.inline (h/code :.clj "(boot-miraj/compile :libraries true)")) " to generate the library.")))

            ;; (h/section {:data-route "user-info" :tabindex "-1"}
            ;;     (h/h1 :.page-title {:tabindex "-1"} "User: " (p/bind!! :params.name))
            ;;   (paper/material {:elevation "-1"}
            ;;     (h/div "This is " (p/bind!! :params.name) "'s section")))

            (h/section {:data-route "dom" :tabindex "-1"}
              (h/h1 :.page-title "Dom")
                (paper/material {:elevation "1"}
                  (h/p "See "
                       (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/local-dom"}
                            "Local DOM Basics and API")
                       ".  Miraj's "
                       (proj/snippet :?clj.inline (h/code :.clj "defcomponent"))
                       " hides the details of constructing a component's local DOM; see the "
                       (h/a {:href "https://github.com/miraj-project/demos/tree/master/hello-world/acme-widgets"} "acme-widgets") " demo for examples.")))

            (h/section {:data-route "styling" :tabindex "-1"}
              (h/h1 :.page-title "Styling")
                (paper/material {:elevation "1"}
                  (h/p "See "
                       (h/a {:href "https://www.polymer-project.org/1.0/docs/devguide/styling"}
                            "Styling local DOM") ".")
                  (h/p "To add styling to a component you have several options.")
                  (h/p "The recommended method is to create a css file
                  named according to the component name.  For example, if your component is acme.sweetness/sweeter, then your CSS file should be acme/sweetness/sweeter.css.  Miraj will inject the CSS into the component definition.  Se the "
                       (h/a {:href "https://github.com/miraj-project/demos/tree/master/hello-world/acme-widgets"} "acme-widgets") " demo for examples.")

                  (h/p "If you need to import external stylesheets, you can use the :css directive of defcomponent, or you can use an edn file, again named according to the component name - e.g. acme/sweetness/sweeter.edn.  See "
                       (h/a {:href "https://github.com/miraj-project/demos/blob/master/hello-world/miraj/src/clj/miraj/demos/hello_world/miraj/sweet.edn"} "hello_world/miraj/sweet.edn") " for details on the syntax.")))

            (h/section {:data-route "boot-miraj" :tabindex "-1"}
              (h/h1 :.page-title "boot-miraj")
                (paper/material {:elevation "1"}
                  (h/p (h/a {:href "https://github.com/miraj-project/boot-miraj"}
                            "boot-miraj")
                       " is a boot task collection for Miraj
                       development.  In general you only need two
                       tasks, compile and link.  See the "
                       (h/a {:href "https://github.com/miraj-project/demos/tree/master/hello-world"}
                            "hello-world")
                       " demos for many examples.")

                  (h/p "To compile and link a library of components:")
                  (proj/snippet :?clj.inline (h/code :.clj "
(deftask lib []
  (comp
   (miraj/compile :components #{}   ;; compile all namespaces
                  ;; :components #{'foo.bar} ;; just this one ns
                  ;; :components #{'foo.bar/baz} ;; just this one component
                  :keep true        ;; keep (or discard) intermediate files
                  :debug true)      ;; log msgs, pretty printed output,
   (miraj/link :libraries #{'acme/widgets} ;; a deflibrary var
               :debug true)))
"))

                  (h/p "To compile and link a page:")
                  (proj/snippet :?clj.inline (h/code :.clj "
(deftask app
  (comp
   (miraj/compile :pages #{'index}
                  :polyfill :lite   ;; inject a Polymer polyfill
                  :debug true)
   (miraj/link :pages #{'index}
               :debug true
               )))
"))

))

            (h/section {:data-route "workflow" :tabindex "-1"}
              (paper/material {:elevation "1"}
                (h/h1 :.page-title "Workflow")
                (h/p "This is the workflow section")))

            (h/section {:data-route "roadmap" :tabindex "-1"}
              (paper/material {:elevation "1"}
                (h/h1 :.page-title "Status & Roadmap")
                (h/p "This is the roadmap section"))))

            ))))
               (paper/toast :#toast
      (h/span :.toast-hide-button {:role "button"
                                   :tabindex "0"
                                   :onclick "app.$.toast.hide()"}
              "Ok"))

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
