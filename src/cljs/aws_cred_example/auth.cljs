(ns aws-cred-example.auth
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
    [reagent.core :as r]
    [aws-cred-example.state :refer [state]]
    [cljs.core.async :refer [put! chan <! >!]]))


; https://developers.google.com/identity/sign-in/web/reference#gapiauth2googleauth

(def user (r/cursor state [:user]))

(defn load-gapi-auth2 []
  (let [c (chan)]
    (.load js/gapi "auth2" #(go (>! c true)))
    c))

(defn auth-instance []
  (.getAuthInstance js/gapi.auth2))

(defn get-google-token []
  (-> (auth-instance) .-currentUser .get .getAuthResponse .-id_token))

(defn handle-user-change
  [u]
  (let [profile (.getBasicProfile u)]
    (reset! user
            {:name       (if profile (.getName profile) nil)
             :image-url  (if profile (.getImageUrl profile) nil)
             :provider-token      (get-google-token)
             :signed-in? (.isSignedIn u)})))

(defn init []
  (go
    (<! (load-gapi-auth2))
    (.init js/gapi.auth2
           (clj->js {"client_id" "496634047757-mi6fvvd7lajtf6qb02o1du9qa4omcqdo.apps.googleusercontent.com"
                     "scope"     "profile"}))
    (.listen (.-currentUser (auth-instance)) handle-user-change)))

(defn auth-status []
  (if-not (:signed-in? @user) [:a {:href "#" :on-click #(.signIn (auth-instance))} "Sign in with Google"]
                              [:div
                               [:p
                                [:strong (:name @user)]
                                [:br]
                                [:img {:src (:image-url @user)}]]
                               [:a {:href "#" :on-click #(.signOut (auth-instance))} "Sign Out"]]))