package com.renanrhoden.wheretolunch.injection

import com.renanrhoden.wheretolunch.repositories.PlacesRepository
import com.renanrhoden.wheretolunch.feature.restaurantstack.RestaurantStackViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class KoinModule {

    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/"

    fun getModule() = module {
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
        viewModel { RestaurantStackViewModel(get()) }
    }
}