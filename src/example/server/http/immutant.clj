(ns example.server.http.immutant
  (:require [integrant.core :as ig]
            [duct.logger :refer [log]]
            [immutant.web :as web]))

(derive :example.server.http/immutant :duct.server/http)

(defmethod ig/init-key :example.server.http/immutant [_ {:keys [logger handler] :as opts}]
  (let [logger (atom logger)
        options (dissoc opts :handler :logger)]
    (log @logger :report ::starting-server (select-keys opts [:port]))
    {:logger logger
     :server (web/run handler options)}))

(defmethod ig/halt-key! :example.server.http/immutant [_ {:keys [server logger]}]
  (log @logger :report ::stopping-server)
  (web/stop server))
