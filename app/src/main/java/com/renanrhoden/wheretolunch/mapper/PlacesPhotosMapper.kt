package com.renanrhoden.wheretolunch.mapper

object PlacesPhotosMapper {
    fun toPlacesPhotosUrl(photoReference: String): String {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=AIzaSyDes_XYPhB8gPzfhvLEfkIjwd7DF7PD4gg"
    }
}