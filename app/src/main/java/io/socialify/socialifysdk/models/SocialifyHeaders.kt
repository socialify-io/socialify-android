package io.socialify.socialifysdk.models

data class SocialifyHeaders (val authToken: String) {
    var contentType: String = "application/json"
    var userAgent: String = "Socialify-android"
    var OS: String = "android-11"
    var appVersion = "0.1"
}
