package com.vina_esima.classexercises.data.cb

import com.vina_esima.classexercises.data.SearchItem
import com.vina_esima.classexercises.data.UnsplashItem

interface UnsplashResult {
    fun onDataFetchedSuccess(images: List<UnsplashItem>)
    fun onDataFetchedFailed()

    fun onSearchSuccess(result: SearchItem)
    fun onSearchFailed()

}