package com.renanrhoden.wheretolunch.injection

import android.app.Application
import com.renanrhoden.wheretolunch.database.RestaurantRoomDatabase
import com.renanrhoden.wheretolunch.feature.restaurantstack.RestaurantStackViewModel
import com.renanrhoden.wheretolunch.repositories.PlacesRepository
import com.renanrhoden.wheretolunch.repositories.RestaurantRoomRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class KoinModule {

    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/"

    fun getModule(application: Application) = module {
        single {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)
            Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
        }
        single { PlacesRepository(get()) }
        single { RestaurantRoomDatabase.getInstance(application) }
        single { RestaurantRoomRepository(get()) }
        viewModel { RestaurantStackViewModel(get(), get()) }
    }
}