;; (def +project+ 'tmp.miraj/website)
;; (def +version+ "1..0.0-SNAPSHOT")

(set-env!

 :asset-paths #{"resources/public"}
 :source-paths #{"src/clj/app" "src/clj/lib"}

 :checkouts '[[miraj/co-dom "1.0.0-SNAPSHOT"] ;; test?
              [miraj/core "1.0.0-SNAPSHOT"]
 ;;              [miraj/polymer "1.0.0-SNAPSHOT"]
 ;;              [miraj/boot-miraj "0.1.0-SNAPSHOT"]
 ;;              ;; [migae/boot-gae "0.2.0-SNAPSHOT"]
              ]

 :dependencies '[[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.562"]
                 [org.clojure/tools.logging "0.3.1"]

                 [miraj/co-dom "1.0.0-SNAPSHOT"]
                 [miraj/core "1.0.0-SNAPSHOT"]
                 ;; [miraj/html "5.1.0-SNAPSHOT"]
                 [miraj/polymer "1.0.0-SNAPSHOT"]
                 [miraj.polymer/paper "1.2.3-SNAPSHOT"]
                 [miraj.polymer/iron "1.2.3-SNAPSHOT"]

                 [hipo "0.5.2"]
                 ;; [adzerk/boot-cljs "2.0.0-OUTPUTFIX" :scope "test"]
                 [adzerk/boot-cljs "2.0.0" :scope "test"]
                 [adzerk/boot-cljs-repl   "0.3.3"] ;; latest release

                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [weasel                  "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]

                 [adzerk/boot-reload "0.5.1" :scope "test"] ;; cljs

                 [miraj/boot-miraj "0.1.0-SNAPSHOT" :scope "test"]

                 ;; [compojure/compojure "1.4.0"]
                 ;; [ring/ring-core "1.4.0"]
                 ;; [ring/ring-servlet "1.4.0"]
                 ;; [ring/ring-defaults "0.1.5"]
                 [ring/ring-devel "1.4.0" :scope "test"]
                 [ns-tracker/ns-tracker "0.3.1"]

                 [pandeiro/boot-http "0.8.3"           :scope "test"]
                 [adzerk/boot-cljs "2.0.0" :scope "test"]
                 ])

(require '[miraj.boot-miraj :as miraj]
         '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[miraj.boot-miraj :as miraj]
         '[pandeiro.boot-http :refer :all]
         '[boot.task.built-in])

(task-options!
 ;; pom  {:project     +project+
 ;;       :version     +version+
 ;;       :description "Example code, boot, miraj, GAE"
 ;;       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}}
 cljs {:compiler-options {:language-in  :ecmascript5-strict
                          :language-out :ecmascript5-strict}
       :optimizations :none})

(deftask monitor
  "build frontend"
  []
  (comp (serve :dir "target" :port 3000)
        (cider)
        (cljs-repl)  ;; run (start-repl) to get the cljs repl
        (watch :exclude #{#"miraj/.*"}) ;;  :verbose true
        (notify :audible true)))

(deftask lib []
  (comp
   ;; we need to generate the component library before the page ns,
   ;; since the latter depends on the former
   (miraj/compile :components #{'proj.widgets 'lib.msgs}
                  :keep true
                  ;; :pprint false)
                  :debug false)
   (miraj/link :libraries #{'proj/gadgets 'lib/msg}
               ;; :verbose true
               :keep true
               :pprint false
               ;; :debug true
               )))

(deftask app
  [p page PAGE sym   "App page to compile."]
  (comp
   (miraj/compile :pages #{'index}
                  ;;:pprint false
                  :polyfill :lite
                  :debug true)
   (miraj/link :pages #{'index}
               ;;:pprint false
               :debug true
               )))

(deftask assetize
  "assetize"
  []
  (sift :add-asset #{"assets"}))

  (comp
   ;; we need the widgets lib in order to build/link the page
   (miraj/compile :components #{'proj.widgets}
                  :keep false
                  ;; :pprint true
                  :debug false)
   (miraj/link    :libraries #{'proj/gadgets}
                  :assets :polymer
                  :debug false)))
(target :dir #{"target/default"} :no-clean true)))


#_(deftask sysdev
    "sys integration dev"
    []
    (comp (watch)
          (notify :audible true)
          (backend)
          (sift :move {#"(.*\.clj$)" (str classes-dir "/$1")})
          (target :no-clean true)))
;; (target :dir #{"target/default"} :no-clean true)))

;; (gae/monitor))

;; to test:  boot gae/run
