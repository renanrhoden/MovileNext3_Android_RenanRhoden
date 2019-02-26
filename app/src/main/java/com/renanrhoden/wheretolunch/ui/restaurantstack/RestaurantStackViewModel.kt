package com.renanrhoden.wheretolunch.ui.restaurantstack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renanrhoden.wheretolunch.repositories.PlacesRepository
import com.renanrhoden.wheretolunch.service.Place
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class RestaurantStackViewModel(
    private val repository: PlacesRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()
    val onSuccess = MutableLiveData<List<Place>>()
    val onError = MutableLiveData<String>()

    init {
        repository.getRestaurants(0L, 0L)
            .subscribeBy(
                onSuccess = {
                    onSuccess.value = it.results.subList(0, 10)
                },
                onError = {
                    onError.value = "An error has occurred retrieving the restaurants. Please, try again later."
                }
            )
            .addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}