package com.example.myplaces.features.main

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplaces.data.Place
import com.example.myplaces.data.PlaceDatabase
import com.example.myplaces.features.map.PlaceActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class PlacesListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlacesListRepository
    val allPlaces: LiveData<List<Place>>

    init {
        val placeDao = PlaceDatabase.getDatabase(application).placeDao()
        repository = PlacesListRepository(placeDao)
        allPlaces = repository.allPlaces
    }

    fun deletePlace(placeName: String) = viewModelScope.launch {
        repository.deletePlace(placeName)
    }

}