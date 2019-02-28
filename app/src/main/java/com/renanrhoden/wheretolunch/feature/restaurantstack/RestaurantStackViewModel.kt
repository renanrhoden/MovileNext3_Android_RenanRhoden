package com.renanrhoden.wheretolunch.feature.restaurantstack

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renanrhoden.wheretolunch.mapper.RestaurantMapper
import com.renanrhoden.wheretolunch.model.Result
import com.renanrhoden.wheretolunch.repositories.PlacesRepository
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class RestaurantStackViewModel(
    private val repository: PlacesRepository
) : ViewModel() {

    private var disposable: Disposable? = null
    private val NUMBER_RESTAURANTS_TO_SHOW = 10

    val onSuccess = MutableLiveData<List<RestaurantCardViewModel>>()
    val onError = MutableLiveData<String>()

    fun loadRestaurants(latitude: Double, longitude: Double) {
        disposable = repository.getRestaurants(latitude, longitude)
            .subscribeBy(
                onSuccess = { place ->
                    onRequestRestaurantsSuccess(place)
                },
                onError = {
                    onRequestRestaurantsFailed(it)
                }
            )
    }

    private fun onRequestRestaurantsFailed(it: Throwable) {
        onError.value = it.message
        Log.d("retrofit", it.message)
    }

    private fun onRequestRestaurantsSuccess(place: Result) {
        val it = place.results.map(RestaurantMapper::toRestaurantCardViewModel)
        val resultsSize = it.size
        if (resultsSize >= NUMBER_RESTAURANTS_TO_SHOW) {
            onSuccess.value = it.subList(0, NUMBER_RESTAURANTS_TO_SHOW)
        } else {
            onSuccess.value = it.subList(0, resultsSize)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}