package com.renanrhoden.wheretolunch.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

class Place(
    @SerializedName("name") val name: String,
    @SerializedName("photos") val photos: List<Photo>,
    @SerializedName("vicinity") val address: String
)