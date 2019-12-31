package com.example.myplaces.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myplaces.R
import com.example.myplaces.data.Place
import com.example.myplaces.toLatLng
import com.google.android.gms.maps.model.LatLng

class PlaceListAdapter internal constructor(
    private val placesListViewModel: PlacesListViewModel,
    val listener: (LatLng) -> Unit
) : RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder>() {


    private var places = emptyList<Place>()

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeTextView: TextView = itemView.findViewById(R.id.textView)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val currentPlace = places[position]
        holder.placeTextView.text = currentPlace.name
        holder.deleteButton.setOnClickListener {
            placesListViewModel.deletePlace(currentPlace.name)
        }
        holder.placeTextView.setOnClickListener {
            listener(currentPlace.destination.toLatLng())
        }
    }

    internal fun setPlaces(places: List<Place>) {
        this.places = places
        notifyDataSetChanged()
    }

    override fun getItemCount() = places.size
}