(ns notebook.views
  (:require [notebook.db :as db]
            [clojure.string :as str]
            [hiccup.page :as hic-p]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "notes: " title)]
   (hic-p/include-css "/css/styles.css")])

(def header-links
  [:div#header-links
   " "
   [:a#linkstyle {:href "/"} "Home"]
   " | "
   [:a#linkstyle {:href "/add-note"} "Add a note"]
   " | "
   [:a#linkstyle {:href "/all-notes"} "View All notes"]
   " "])
(def title
  [:div#title
  [:h2 "H3Notebook"]
  [:p#subtitle "- small webapp to store and display notes"]])

(defn home-page
  []
  (hic-p/html5
   (gen-page-head "Home")
   header-links
   title
   [:p "This app was created for practice during long lectures."]))

(defn add-note-page
  []
  (hic-p/html5
   (gen-page-head "Add note")
   header-links
   title
   [:h1 "Add a note"]
   [:form {:action "/add-note" :method "POST"}
    [:p "Title: " [:input {:type "text" :name "header"}]]
    [:p "Content: " [:input {:type "text" :name "content"}]]
    [:p [:input {:type "submit" :value "submit note"}]]]))

(defn add-note-results-page
  [{:keys [header content]}]
  (let [id (db/add-note-to-db header content)]
    (hic-p/html5
     (gen-page-head "Added a note")
     header-links
     title
     [:h1 "Added a note"]
     [:p "Added [" header ", " content "] (id: " id ") to the db. "
      [:a {:href (str "/note/" id)} "See for yourself"]
      "."])))

(defn note-page
  [note-id]
  (let [{header :header content :content} (db/get-note note-id)]
    (hic-p/html5
     (gen-page-head (str "note " note-id))
     header-links
     title
     [:h1 "A Single note"]
     [:h3 "Title: " [:h4 header]]
     [:h3 "Content: " [:h4 content]])))

(defn all-notes-page
  []
  (let [all-notes (db/get-all-notes)]
    (hic-p/html5
     (gen-page-head "All notes in the db")
     header-links
     title
     [:h1 "All notes"]
      (for [note all-notes]
       [:a#kepulikonstit {:href (str "/note/"(:id note))} [:h (str (:id note) " ")] [:h (:header note)][:br] ]))))