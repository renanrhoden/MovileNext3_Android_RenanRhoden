package com.renanrhoden.wheretolunch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.renanrhoden.wheretolunch.database.entities.Restaurant
import org.jetbrains.anko.doAsync

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
                    .addCallback(object : Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            doAsync {
                                getInstance(context).restaurantDao.deteleAll()
                            }
                        }
                    })
                    .build().also {
                        instance = it
                    }
            }
        }
    }
}