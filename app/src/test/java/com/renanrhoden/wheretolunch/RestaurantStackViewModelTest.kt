package com.renanrhoden.wheretolunch

import com.renanrhoden.wheretolunch.feature.restaurantstack.RestaurantStackViewModel
import com.renanrhoden.wheretolunch.mapper.RestaurantMapper
import com.renanrhoden.wheretolunch.model.Photo
import com.renanrhoden.wheretolunch.model.Place
import com.renanrhoden.wheretolunch.model.Result
import com.renanrhoden.wheretolunch.repositories.PlacesRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RestaurantStackViewModelTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule = SynchronousTestSchedulerRule()

    lateinit var viewmodel: RestaurantStackViewModel
    val repository: PlacesRepository = mockk(relaxed = true)
    val mockSuccessGreater10 = listOf(
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), ""),
        Place("", listOf(Photo(" ")), "")
    )

    @Before
    fun setUp() {
        viewmodel = RestaurantStackViewModel(repository)
    }

    @Test
    fun whenGetRestaurants_numberOfRestaurantsGreaterThan10_mustSetFirst10ToLiveData() {
        every { repository.getRestaurants(any(), any()) } returns Single.just(Result(mockSuccessGreater10))
        viewmodel = RestaurantStackViewModel(repository)
        assertEquals(mockSuccessGreater10.map(RestaurantMapper::toRestaurantCardViewModel), viewmodel.onSuccess.value)
    }
}