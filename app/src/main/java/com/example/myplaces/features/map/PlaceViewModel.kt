package com.example.myplaces.features.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplaces.data.Place
import com.example.myplaces.data.PlaceDatabase
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PlaceRepository

    init {
        val placeDao = PlaceDatabase.getDatabase(application).placeDao()
        repository = PlaceRepository(placeDao)
    }

    fun insertPlace(place: Place) = viewModelScope.launch {
        repository.insertPlace(place)
    }

}