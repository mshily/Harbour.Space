package com.vina_esima.final_project

import com.vina_esima.final_project.data.data.SearchItem
import com.vina_esima.final_project.data.data.UnsplashItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
private const val ACCESS_KEY = "x916LwiMNi5UyFvYukW11frzXVXJsCFKYsG1ZgmjEsk"
private const val AUTH_HEADER = "Authorization: Client-ID $ACCESS_KEY"


interface UnsplashApi {
    @Headers(AUTH_HEADER)
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashItem>>

    @Headers(AUTH_HEADER)
    @GET("/search/photos")
    fun SearchPhotos(@Query ("query") query: String) : Call<SearchItem>
}