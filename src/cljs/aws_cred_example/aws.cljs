(ns aws-cred-example.aws
  (:require-macros [reagent.ratom :refer [run!]])
  (:require [reagent.core :as r]
            [aws-cred-example.state :refer [state]]))

(def aws-user-id (r/cursor state [:aws-user-id]))
(def provider-token (r/cursor state [:user :provider-token]))

(defn update-credentials
  [token]

  (aset js/AWS.config.credentials.params "Logins" (clj->js (if token {"accounts.google.com" token} {})))
  (aset js/AWS.config.credentials "expired" (clj->js true))

  (.get js/AWS.config.credentials #(let [id (aget js/AWS.config.credentials "identityId")]
                                    (if-not id (update-credentials nil))
                                    (reset! aws-user-id id))))
(defn init []
  (aset js/AWS.config "region" "us-east-1")
  (aset js/AWS.config
        "credentials"
        (js/AWS.CognitoIdentityCredentials. (clj->js {"IdentityPoolId" "us-east-1:354453dd-4512-4d6a-92ea-c23c963cbfee"})))
  (run! (update-credentials @provider-token)))

(defn aws-status [] [:p "AWS user id: " @aws-user-id])