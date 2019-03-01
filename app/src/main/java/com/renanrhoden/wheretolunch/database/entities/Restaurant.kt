package com.renanrhoden.wheretolunch.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey
    val name: String,
    val address: String,
    val photoReference: String
)