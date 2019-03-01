package com.renanrhoden.wheretolunch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renanrhoden.wheretolunch.database.entities.Restaurant

@Dao
interface RestaurantDao {

    @Query("DELETE FROM restaurants")
    fun deteleAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restaurant: Restaurant)

    @Query("SELECT * FROM restaurants")
    fun getAll(): LiveData<List<Restaurant>>
}