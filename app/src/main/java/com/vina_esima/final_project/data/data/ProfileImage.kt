package com.vina_esima.final_project.data.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileImage(
    val large: String?,
    val medium: String?,
    val small: String?
) : Parcelable