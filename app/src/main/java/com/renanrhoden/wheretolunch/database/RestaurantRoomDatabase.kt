package com.renanrhoden.wheretolunch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.renanrhoden.wheretolunch.database.entities.Restaurant

@Database(entities = [Restaurant::class], version = 1)
abstract class RestaurantRoomDatabase : RoomDatabase() {

    abstract val restaurantDao: RestaurantDao

    companion object {

        private var instance: RestaurantRoomDatabase? = null

        fun getInstance(context: Context): RestaurantRoomDatabase {
            return instance?.let {
                instance
            } ?: synchronized(RestaurantRoomDatabase::class) {
                Room.databaseBuilder(context, RestaurantRoomDatabase::class.java, "restaurants.db")
                    .build().also {
                        instance = it
                    }
            }
        }
    }
}