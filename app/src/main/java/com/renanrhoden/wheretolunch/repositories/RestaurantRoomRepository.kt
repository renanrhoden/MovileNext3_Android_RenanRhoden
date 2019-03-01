package com.renanrhoden.wheretolunch.repositories

import com.renanrhoden.wheretolunch.database.RestaurantRoomDatabase
import com.renanrhoden.wheretolunch.database.entities.Restaurant
import org.jetbrains.anko.doAsync

class RestaurantRoomRepository(db: RestaurantRoomDatabase) {

    private val dao = db.restaurantDao
    val allRestaurants = dao.getAll()

    fun insert(restaurant: Restaurant) {
        doAsync {
            dao.insert(restaurant)
        }
    }
}