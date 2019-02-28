package com.renanrhoden.wheretolunch.repositories

import com.renanrhoden.wheretolunch.model.Result
import com.renanrhoden.wheretolunch.service.PlacesApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class PlacesRepository(private val retrofit: Retrofit) {

    fun getRestaurants(latitude: Double, longitude: Double): Single<Result> {
        return retrofit.create(PlacesApi::class.java)
            .getPlaces("$latitude,$longitude")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}