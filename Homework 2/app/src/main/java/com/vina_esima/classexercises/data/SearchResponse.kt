package com.vina_esima.classexercises.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchItem (
    val total: Int?,
    val total_pages: Int?,
    val results: List<UnsplashItem>
) : Parcelable