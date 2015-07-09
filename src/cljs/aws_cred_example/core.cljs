(ns aws-cred-example.core
  ^:figwheel-always
  (:require
    [reagent.core :as r]
    [aws-cred-example.aws :as aws]
    [aws-cred-example.auth :as auth]))

(enable-console-print!)

(defn app []
  [:div
   [auth/auth-status]
   [aws/aws-status]])

(r/render [app] (.getElementById js/document "app"))

(defonce _ (do
             (auth/init)
             (aws/init)))
