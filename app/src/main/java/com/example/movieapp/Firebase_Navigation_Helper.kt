package com.example.movieapp

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

fun Firebase_Navigation_Helper(firebaseAnalytics: FirebaseAnalytics, screenName: String) {
    val bundle = Bundle().apply {
        putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        putString(FirebaseAnalytics.Param.SCREEN_CLASS, "Composable")
    }
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
}
