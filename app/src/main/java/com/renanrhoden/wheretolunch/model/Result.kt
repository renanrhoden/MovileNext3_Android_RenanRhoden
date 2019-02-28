package com.renanrhoden.wheretolunch.model

import com.google.gson.annotations.SerializedName

class Result(@SerializedName("results") val results: List<Place>)