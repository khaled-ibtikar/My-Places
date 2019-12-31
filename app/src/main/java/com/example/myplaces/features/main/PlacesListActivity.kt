package com.example.myplaces.features.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myplaces.R
import com.example.myplaces.features.map.PlaceActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*

class PlacesListActivity : AppCompatActivity() {

    private lateinit var placesListViewModel: PlacesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        placesListViewModel = ViewModelProvider(this).get(PlacesListViewModel::class.java)

        initRecyclerView()
        setListeners()
    }

    private fun setListeners() {
        fab.setOnClickListener {
            startActivity(Intent(this, PlaceActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        val adapter =
            PlaceListAdapter(placesListViewModel) {latLan: LatLng ->
                    navigateToPlace(latLan)
            }
        recyclerView.adapter = adapter
        placesListViewModel.allPlaces.observe(this, Observer { places ->
            places?.let { adapter.setPlaces(it) }
        })
    }

    private fun navigateToPlace(latLng: LatLng) {
        val intent = Intent(this, PlaceActivity::class.java)
        intent.putExtra("destination", latLng)
        this.startActivity(intent)
    }

}
