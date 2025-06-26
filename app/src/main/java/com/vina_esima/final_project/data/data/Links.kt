package com.vina_esima.final_project.data.data
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
    val download: String?,
    val download_location: String?,
    val html: String?,
    val self: String?
) : Parcelable