package com.vina_esima.final_project.data.data
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchItem (
    val total: Int?,
    val total_pages: Int?,
    val results: List<UnsplashItem>
) : Parcelable