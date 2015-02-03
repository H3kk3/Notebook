(ns notebook.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname "db/notebook"})

(defn add-note-to-db
  [header content]
  (let [results (sql/with-connection db-spec
                  (sql/insert-record :notes
                                     {:header header :content content}))]
    (assert (= (count results) 1))
    (first (vals results))))

(defn get-note
  [note-id]
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["select header, content from notes where id = ?" note-id]
                    (doall res)))]
    (assert (= (count results) 1))
    (first results)))

(defn get-all-notes
  []
  (let [results (sql/with-connection db-spec
                  (sql/with-query-results res
                    ["select id, header, content from notes"]
                    (doall res)))]
    results))