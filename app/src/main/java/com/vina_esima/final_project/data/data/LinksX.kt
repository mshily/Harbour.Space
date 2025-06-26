package com.vina_esima.final_project.data.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksX(
    val html: String?,
    val likes: String?,
    val photos: String?,
    val portfolio: String?,
    val self: String?
) : Parcelable