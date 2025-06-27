package com.vina_esima.final_project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vina_esima.final_project.data.data.SearchItem
import com.vina_esima.final_project.data.data.UnsplashApiProvider
import com.vina_esima.final_project.data.data.UnsplashItem
import com.vina_esima.final_project.data.data.cb.UnsplashResult
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
class UnsplashViewModel(
    private val provider: UnsplashApiProvider = UnsplashApiProvider()
) : ViewModel(), UnsplashResult {


    private val _images = MutableLiveData<List<UnsplashItem>>(emptyList())
    val images: LiveData<List <UnsplashItem>> = _images


    init {
        fetchAll()
        Log.d("UnsplashViewModel", "ViewModel created, starting fetchAll()")

    }

    fun fetchAll() {
        provider.fetchImages(this)
    }

    fun search(query: String) {
        if (query.isBlank()) provider.fetchImages(this)
        else provider.searchImages(this, query)
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        _images.value = images
    }

    override fun onDataFetchedFailed() {
    }

    override fun onSearchSuccess(result: SearchItem) {
        _images.value = result.results
    }

    override fun onSearchFailed() {
    }
}