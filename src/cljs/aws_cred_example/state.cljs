(ns aws-cred-example.state
  (:require
    [reagent.core :as r]))

(def state (r/atom {:user {:blank true}}))
