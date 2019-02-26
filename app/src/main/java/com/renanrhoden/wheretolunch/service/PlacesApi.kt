package com.renanrhoden.wheretolunch.service

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {

    @GET("json?&radius=1500&type=restaurant&key=AIzaSyDes_XYPhB8gPzfhvLEfkIjwd7DF7PD4gg")
    fun getPlaces(@Query("location") latLong: String): Single<Result>
}

class Place(@SerializedName("name") val name: String)
class Result(@SerializedName("results") val results: List<Place>)