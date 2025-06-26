package com.vina_esima.final_project.data.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UnsplashItem(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val current_user_collections: List<CurrentUserCollection?>?,
    val description: String?,
    val height: Int?,
    val id: String?,
    val liked_by_user: Boolean?,
    val likes: Int?,
    val views: Int?,
    val downloads: Int?,
    val links: Links?,
    val updated_at: String?,
    val urls: Urls?,
    val user: User?,
    val width: Int?
) : Parcelable