package com.renanrhoden.wheretolunch.repositories

import androidx.lifecycle.LiveData
import com.renanrhoden.wheretolunch.database.RestaurantRoomDatabase
import com.renanrhoden.wheretolunch.database.entities.Restaurant
import org.jetbrains.anko.doAsync

class RestaurantRoomRepository(db: RestaurantRoomDatabase) {

    private val dao = db.restaurantDao

    fun insert(restaurant: Restaurant) {
        doAsync {
            dao.insert(restaurant)
        }
    }

    fun getAllRestaurants(): LiveData<List<Restaurant>> {
        return dao.getAll()
    }
}