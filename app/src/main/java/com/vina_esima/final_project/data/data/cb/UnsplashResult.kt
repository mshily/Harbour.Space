package com.vina_esima.final_project.data.data.cb

import com.vina_esima.final_project.data.data.SearchItem
import com.vina_esima.final_project.data.data.UnsplashItem


interface UnsplashResult {
    fun onDataFetchedSuccess(images: List<UnsplashItem>)
    fun onDataFetchedFailed()

    fun onSearchSuccess(result: SearchItem)
    fun onSearchFailed()
}