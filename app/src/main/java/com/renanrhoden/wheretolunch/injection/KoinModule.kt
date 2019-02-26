package com.renanrhoden.wheretolunch.injection

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.renanrhoden.wheretolunch.ui.restaurantstack.RestaurantStackViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KoinModule {

    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/"

    fun getModule() = module {
        single {
            Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

//        viewModel { RestaurantStackViewModel() }
    }
}