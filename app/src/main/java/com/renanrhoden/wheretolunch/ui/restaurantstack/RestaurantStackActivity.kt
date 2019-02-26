package com.renanrhoden.wheretolunch.ui.restaurantstack

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.renanrhoden.wheretolunch.R
import com.renanrhoden.wheretolunch.databinding.ActivityMainBinding
import com.renanrhoden.wheretolunch.service.PlacesApi
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.Retrofit


class RestaurantStackActivity : AppCompatActivity() {

    val stackViewModel: RestaurantStackViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null
    val LOCATION_PORMISSION_REQUEST_CODE = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        observeViewModel()

        requestLocationPermission()

        val manager = CardStackLayoutManager(this)
        val adapter = null
    }

    private fun observeViewModel() {
        stackViewModel.onSuccess.observe(this, Observer {

        })

        stackViewModel.onError.observe(this, Observer {

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

    private fun onLocationGranted() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation.addOnSuccessListener {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PORMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
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
}
