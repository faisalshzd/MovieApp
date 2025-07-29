package com.example.movieapp.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object AnalyticsTracker {

    fun trackEvent(firebaseAnalytics: FirebaseAnalytics, eventName: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}
