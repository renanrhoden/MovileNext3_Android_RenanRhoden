package com.renanrhoden.wheretolunch.feature.restaurantstack

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renanrhoden.wheretolunch.database.entities.Restaurant
import com.renanrhoden.wheretolunch.mapper.RestaurantMapper
import com.renanrhoden.wheretolunch.mapper.RestaurantMapper.toRestaurant
import com.renanrhoden.wheretolunch.model.Place
import com.renanrhoden.wheretolunch.model.Result
import com.renanrhoden.wheretolunch.repositories.PlacesRepository
import com.renanrhoden.wheretolunch.repositories.RestaurantRoomRepository
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Direction.Left
import com.yuyakaido.android.cardstackview.Direction.Right
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class RestaurantStackViewModel(
    private val placesRepository: PlacesRepository,
    private val restaurantsRepository: RestaurantRoomRepository
) : ViewModel() {

    private var disposable: Disposable? = null
    private val NUMBER_RESTAURANTS_TO_SHOW = 10
    private var max_restaurants = NUMBER_RESTAURANTS_TO_SHOW

    val onSuccess = MutableLiveData<List<RestaurantCardViewModel>>()
    val onError = MutableLiveData<String>()
    val swipe = MutableLiveData<Direction>()
    val openRestaurantsLiked = MutableLiveData<List<Restaurant>>()

    var places: List<Place>? = null

    fun loadRestaurants(latitude: Double, longitude: Double) {
        disposable = placesRepository.getRestaurants(latitude, longitude)
            .subscribeBy(
                onSuccess = { places ->
                    onRequestRestaurantsSuccess(places)
                },
                onError = {
                    onRequestRestaurantsFailed(it)
                }
            )
    }

    private fun onRequestRestaurantsFailed(it: Throwable) {
        onError.value = it.message
    }

    private fun onRequestRestaurantsSuccess(places: Result) {
        val it = places.results
            .also { this@RestaurantStackViewModel.places = it }
            .map(RestaurantMapper::toRestaurantCardViewModel)
        val resultsSize = it.size
        if (resultsSize >= NUMBER_RESTAURANTS_TO_SHOW) {
            onSuccess.value = it.subList(0, NUMBER_RESTAURANTS_TO_SHOW)
        } else {
            onSuccess.value = it.subList(0, resultsSize)
            max_restaurants = resultsSize
        }
    }

    fun onLikeClick() {
        swipe.value = Right
    }

    fun onDislikeClick() {
        swipe.value = Left
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun onSwipe(direction: Direction, topPosition: Int) {
        if (direction == Direction.Right) {
            insertDatabaseSwipedRestaurant(topPosition)
        }

        if (topPosition == max_restaurants){
            restaurantsRepository.getAll()
                .subscribeBy {
                    openRestaurantsLiked.value = it
                }
        }
    }

    private fun insertDatabaseSwipedRestaurant(topPosition: Int) {
        places?.let {
            try {
                restaurantsRepository.insert(toRestaurant(it[topPosition - 1]))
            } catch (e: Throwable){
                Log.i("DEBUG INSERT", e.message)
            }
        }
    }
}