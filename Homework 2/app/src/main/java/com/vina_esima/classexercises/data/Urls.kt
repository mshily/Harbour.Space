package com.vina_esima.classexercises.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Urls(
    val full: String?,
    val raw: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
) : Parcelable