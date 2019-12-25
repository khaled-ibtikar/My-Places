package com.example.myplaces.features.map

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myplaces.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_maps.*


class PlaceActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var placeViewModel: PlaceViewModel
    private lateinit var mMap: GoogleMap

    private var selectedPlace: Place? = null
    private var destination: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        initAutoCompleteFragment()
        initSupportMapFragment()
        initPlacesApi()
        setListeners()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Check if a place was selected
        destination = intent.getParcelableExtra("destination")
        if (destination != null) {
            mMap.apply {
                addMarker(MarkerOptions().position(destination!!))

                val cameraPosition = CameraPosition.Builder()
                    .target(destination)
                    .zoom(17f)
                    .build()
                animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }
    }

    private fun initAutoCompleteFragment() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        autocompleteFragment.setCountry("eg")
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            @Override
            override fun onPlaceSelected(place: Place) {
                mMap.apply {
                    selectedPlace = place
                    addMarker(MarkerOptions().position(place.latLng!!))

                    val cameraPosition = CameraPosition.Builder()
                        .target(place.latLng)
                        .zoom(17f)
                        .build()
                    animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
                Log.i(TAG, "Place: " + place.name + ", " + place.id)
            }

            @Override
            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    private fun initSupportMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    }

    private fun initPlacesApi() {
        val apiKey = getString(R.string.google_maps_key)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
    }

    private fun setListeners() {
        addPlaceButton.setOnClickListener {
            if (selectedPlace != null) {
                placeViewModel.insertPlace(
                    com.example.myplaces.data.Place(
                        selectedPlace!!.name!!,
                        selectedPlace!!.latLng!!.toString()
                    )
                )
                Toast.makeText(this, "Place Added Successfully!", Toast.LENGTH_SHORT).show()
                this@PlaceActivity.finish()
            } else
                Toast.makeText(this, "Please select a place first!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object
    val TAG = "Map"
}

