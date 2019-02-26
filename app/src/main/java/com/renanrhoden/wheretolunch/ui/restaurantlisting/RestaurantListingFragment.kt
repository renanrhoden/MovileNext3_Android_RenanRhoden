package com.renanrhoden.wheretolunch.ui.restaurantlisting

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renanrhoden.wheretolunch.R

class RestaurantListingFragment : Fragment() {

    companion object {
        fun newInstance() = RestaurantListingFragment()
    }

    private lateinit var viewModel: RestaurantListingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.restaurant_listing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RestaurantListingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
