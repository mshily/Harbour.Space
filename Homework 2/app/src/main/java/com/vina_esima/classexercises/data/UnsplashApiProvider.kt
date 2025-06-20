package com.vina_esima.classexercises.data

import UnsplashApi
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vina_esima.classexercises.data.cb.UnsplashResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.unsplash.com/"

private val retrofit by lazy {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()

    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create<UnsplashApi> ()
}

class UnsplashApiProvider() {
    fun fetchImages(cb: UnsplashResult) {

        Log.d("UNSPLASH", "fetchImages")

        retrofit.fetchPhotos().enqueue(object : Callback<List<UnsplashItem>> {
            override fun onResponse(
                call: Call<List<UnsplashItem>>,
                response: Response<List<UnsplashItem>>
            ) {
                Log.d("UNSPLASH", "code=${response.code()}")
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<List<UnsplashItem>>, t: Throwable) {
                Log.d("UNSPLASH", "network error=${t.message}")
                cb.onDataFetchedFailed()
            }
        })
    }

    fun searchImages(cb: UnsplashResult, query: String) {
        retrofit.SearchPhotos(query).enqueue(object : Callback<SearchItem> {
            override fun onResponse(
                call: Call<SearchItem>,
                response: Response<SearchItem>
            ) {
                Log.d("UNSPLASH", "code=${response.code()}")
                if (response.isSuccessful && response.body() != null) {
                    cb.onSearchSuccess(response.body()!!)
                } else {
                    cb.onSearchFailed()
                }
            }

            override fun onFailure(call: Call<SearchItem>, t: Throwable) {
                Log.d("UNSPLASH", "network error=${t.message}")
                cb.onSearchFailed()
            }
        })
    }
}
