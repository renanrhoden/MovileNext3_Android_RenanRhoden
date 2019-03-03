package com.renanrhoden.wheretolunch.feature.restaurantlisting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.renanrhoden.wheretolunch.R
import com.renanrhoden.wheretolunch.database.entities.Restaurant

class RestaurantListingActivity : AppCompatActivity() {
    private val adapter by lazy {
        RestaurantListingAdapter(this)
    }

    lateinit var binding: com.renanrhoden.wheretolunch.databinding.RestaurantListingActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableArrayListExtra<Restaurant>(EXTRA_RESTAURANTS_LIST)?.let {
            adapter.list = it
        } ?: throw RuntimeException("Must provide a list of restaurants to show")

        binding = DataBindingUtil.setContentView(this, R.layout.restaurant_listing_activity)
        binding.list.run {
            adapter = this@RestaurantListingActivity.adapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    companion object {
        private const val EXTRA_RESTAURANTS_LIST = "EXTRA_RESTAURANTS_LIST"

        fun getIntent(activity: Activity, restaurants: List<Restaurant>): Intent {
            return Intent(activity, RestaurantListingActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRA_RESTAURANTS_LIST, ArrayList(restaurants))
            }
        }
    }
}
