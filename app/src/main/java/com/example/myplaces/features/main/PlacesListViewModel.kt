package com.example.myplaces.features.main

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myplaces.data.Place
import com.example.myplaces.data.PlaceDatabase
import com.example.myplaces.features.map.PlaceActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class PlacesListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlacesListRepository
    val allPlaces: LiveData<List<Place>>
    var applicationContext : Application
    init {

        val wordsDao = PlaceDatabase.getDatabase(application,viewModelScope).placeDao()
        repository = PlacesListRepository(wordsDao)
        allPlaces = repository.allPlaces
        applicationContext = application
    }

    fun deletePlace(place: String) = viewModelScope.launch {
        repository.deletePlace(place)
    }

    fun navigateToPlace(latLng: LatLng) {
        val intent = Intent(applicationContext,PlaceActivity::class.java)
        intent.putExtra("destination",latLng)
        applicationContext.startActivity(intent)
    }
}