(ns notebook.handler
  (:require [notebook.views :as views]
            [compojure.core :as cc]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(cc/defroutes app-routes
  (cc/GET "/"
       []
       (views/home-page))
  (cc/GET "/add-note"
       []
       (views/add-note-page))
  (cc/POST "/add-note"
        {params :params}
        (views/add-note-results-page params))
  (cc/GET "/note/:note-id"
       [note-id]
       (views/note-page note-id))
  (cc/GET "/all-notes"
       []
       (views/all-notes-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))