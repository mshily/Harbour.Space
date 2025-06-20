package com.vina_esima.classexercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vina_esima.classexercises.data.SearchItem
import com.vina_esima.classexercises.data.UnsplashApiProvider
import com.vina_esima.classexercises.data.UnsplashItem
import com.vina_esima.classexercises.data.cb.UnsplashResult

class GalleryViewModel(
    private val provider: UnsplashApiProvider = UnsplashApiProvider()
) : ViewModel(), UnsplashResult {


    private val _images = MutableLiveData<List<UnsplashItem>>(emptyList())
    val images: LiveData<List <UnsplashItem>> = _images

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _lastQuery = MutableLiveData("")
    val lastQuery = _lastQuery

    init {
        fetchAll()
    }

    fun fetchAll() {
        _isLoading.value = true
        _lastQuery.value = ""
        provider.fetchImages(this)
    }

    fun search(query: String) {
        _isLoading.value = true
        _lastQuery.value = query
        if (query.isBlank()) provider.fetchImages(this)
        else provider.searchImages(this, query)
    }

    fun refresh() {
        val last = _lastQuery.value.orEmpty()
        if (last.isBlank()) fetchAll()
        else search(last)
    }
 
    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        _images.value = images
        _isLoading.value = false
    }

    override fun onDataFetchedFailed() {
        _isLoading.value = false
    }

    override fun onSearchSuccess(result: SearchItem) {
        _images.value = result.results
        _isLoading.value = false
    }

    override fun onSearchFailed() {
        _isLoading.value = false
    }
}