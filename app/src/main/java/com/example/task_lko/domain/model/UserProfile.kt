package com.example.task_lko.domain.model

data class UserProfile(
    val username: String,
    val name: String,
    val avatarUrl: String,
    val city: String,
    val country: String,
    val website: String,
    val socialProfiles: List<SocialProfile>,
    val followers: Int,
    val following: Int,
    val shots: Int,
    val collections: Int
) {
    data class SocialProfile(
        val platform: String,
        val url: String
    )
    
    val location: String
        get() = "$city, $country"
}