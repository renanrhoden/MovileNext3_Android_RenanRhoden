package com.renanrhoden.wheretolunch.ui.restaurantlisting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renanrhoden.wheretolunch.R

class RestaurantListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_listing_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RestaurantListingFragment.newInstance())
                .commitNow()
        }
    }

}
