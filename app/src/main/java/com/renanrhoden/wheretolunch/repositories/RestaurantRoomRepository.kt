package com.renanrhoden.wheretolunch.repositories

import com.renanrhoden.wheretolunch.database.RestaurantRoomDatabase
import com.renanrhoden.wheretolunch.database.entities.Restaurant
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

class RestaurantRoomRepository(db: RestaurantRoomDatabase) {

    private val dao = db.restaurantDao

    fun insert(restaurant: Restaurant) {
        doAsync {
            dao.insert(restaurant)
        }
    }

    fun getAll(): Single<List<Restaurant>> {
        return dao.getAll()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}