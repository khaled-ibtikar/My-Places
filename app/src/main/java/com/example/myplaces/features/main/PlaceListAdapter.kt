package com.example.myplaces.features.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myplaces.R
import com.example.myplaces.data.Place
import com.google.android.gms.maps.model.LatLng

class PlaceListAdapter internal constructor(
    context: Context,
    private val placesListViewModel: PlacesListViewModel
) : RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var places = emptyList<Place>()

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeTextView: TextView = itemView.findViewById(R.id.textView)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val current = places[position]
        holder.placeTextView.text = current.name
        holder.deleteButton.setOnClickListener {
            placesListViewModel.deletePlace(current.name)
        }
        holder.placeTextView.setOnClickListener {
            val latLng = current.destination.split('(', ')')[1].split(',')

            placesListViewModel.navigateToPlace(
                LatLng(
                    latLng[0].toDouble(),
                    latLng[1].toDouble()
                )
            )
        }


    }

    internal fun setWords(places: List<Place>) {
        this.places = places
        notifyDataSetChanged()
    }

    override fun getItemCount() = places.size
}