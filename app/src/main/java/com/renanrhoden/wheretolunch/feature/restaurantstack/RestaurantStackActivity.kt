package com.renanrhoden.wheretolunch.feature.restaurantstack

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.renanrhoden.wheretolunch.R
import com.renanrhoden.wheretolunch.databinding.ActivityMainBinding
import com.renanrhoden.wheretolunch.feature.restaurantlisting.RestaurantListingActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantStackActivity : AppCompatActivity(), CardStackListener {

    private val LOCATION_PORMISSION_REQUEST_CODE = 1010
    private val stackViewModel: RestaurantStackViewModel by viewModel()
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult?) {
            super.onLocationResult(location)
            location ?: return

            location.lastLocation.run {
                stackViewModel.loadRestaurants(latitude, longitude)
            }
        }
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMainBinding
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { RestaurantStackAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        requestLocationPermission()


        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter

        binding.viewModel = stackViewModel
        observeViewModel(adapter)
    }

    private fun observeViewModel(adapter: RestaurantStackAdapter) {
        stackViewModel.onSuccess.observe(this, Observer {
            adapter.list = it
        })

        stackViewModel.onError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        stackViewModel.swipe.observe(this, Observer {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(it)
                .setDuration(200)
                .setInterpolator(AccelerateInterpolator())

                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.cardStackView.swipe()
        })
        stackViewModel.openRestaurantsLiked.observe(this, Observer {
            startActivity(RestaurantListingActivity.getIntent(this, it))
        })
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PORMISSION_REQUEST_CODE
            )
        } else {
            onLocationGranted()
        }
    }

    @SuppressLint("MissingPermission")
    private fun onLocationGranted() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.run {
                stackViewModel.loadRestaurants(latitude, longitude)
            } ?: fusedLocationClient.requestLocationUpdates(LocationRequest.create(), locationCallback, null)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PORMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)) {
                    onLocationGranted()
                } else {
                    requestLocationPermission()
                }
                return
            }
            else -> {
            }
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction) {
        stackViewModel.onSwipe(direction, manager.topPosition)
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardRewound() {
    }
}
