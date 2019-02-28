package com.renanrhoden.wheretolunch.mapper

import com.renanrhoden.wheretolunch.mapper.PlacesPhotosMapper.toPlacesPhotosUrl
import com.renanrhoden.wheretolunch.model.Place
import com.renanrhoden.wheretolunch.feature.restaurantstack.RestaurantCardViewModel

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
}