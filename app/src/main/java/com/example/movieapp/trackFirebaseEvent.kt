package com.example.movieapp

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics


fun trackFirebaseEvent(firebaseAnalytics: FirebaseAnalytics, eventName: String, bundle: Bundle) {
    firebaseAnalytics.logEvent(eventName, bundle)
}
