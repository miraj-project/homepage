(ns proj.widgets.devices)

(enable-console-print!)

(println "LOADING proj.widgets.devices")

(defn ready [this]
  (println "devices READY"))

#_(defn ajax [this]
  (println "devices AJAX")
  (println "this:" this)
  (println "r:" r)
  (println "data: " (aget this "devices")))


