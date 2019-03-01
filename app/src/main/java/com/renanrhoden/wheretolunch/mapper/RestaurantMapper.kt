package com.renanrhoden.wheretolunch.mapper

import com.renanrhoden.wheretolunch.database.entities.Restaurant
import com.renanrhoden.wheretolunch.feature.restaurantstack.RestaurantCardViewModel
import com.renanrhoden.wheretolunch.mapper.PlacesPhotosMapper.toPlacesPhotosUrl
import com.renanrhoden.wheretolunch.model.Place

object RestaurantMapper {
    fun toRestaurantCardViewModel(place: Place): RestaurantCardViewModel {
        with(place) {
            return RestaurantCardViewModel(
                name,
                toPlacesPhotosUrl(photos.firstOrNull()?.photoReference ?: ""),
                address
            )
        }
    }

    fun toRestaurant(place: Place): Restaurant {
        with(place) {
            return Restaurant(
                name,
                address,
                photos.firstOrNull()?.photoReference ?: ""
            )
        }
    }
}