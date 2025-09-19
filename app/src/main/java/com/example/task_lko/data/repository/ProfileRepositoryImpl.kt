package com.example.task_lko.data.repository

import com.example.task_lko.data.datasource.ProfileRemoteDataSource
import com.example.task_lko.domain.model.UserProfile
import com.example.task_lko.domain.repository.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {
    
    override suspend fun getProfile(): Result<UserProfile> {
        return try {
            val result = remoteDataSource.getProfile()
            result.map { userResponse ->
                val user = userResponse.user
                UserProfile(
                    username = user.username,
                    name = user.name,
                    avatarUrl = user.avatar,
                    city = user.location.city,
                    country = user.location.country,
                    website = user.social.website,
                    socialProfiles = user.social.profiles.map { profile ->
                        UserProfile.SocialProfile(
                            platform = profile.platform,
                            url = profile.url
                        )
                    },
                    followers = user.statistics.followers,
                    following = user.statistics.following,
                    shots = user.statistics.activity.shots,
                    collections = user.statistics.activity.collections
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}