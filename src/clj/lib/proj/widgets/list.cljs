(ns proj.widgets.list)

(enable-console-print!)

(println "LOADING proj.widgets.list")

(defn ready [this]
  (println "List READY")
  (aset this "items"
        #js ["Responsive Web App boilerplate",
             "Iron Elements and Paper Elements",
             "End-to-end Build Tooling (including Vulcanize)",
             "Unit testing with Web Component Tester",
             "Routing with Page.js",
             "Offline support with the Platinum Service Worker Elements"]))


