package com.renanrhoden.wheretolunch.feature.restaurantlisting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.renanrhoden.wheretolunch.R
import com.renanrhoden.wheretolunch.database.entities.Restaurant
import com.renanrhoden.wheretolunch.databinding.LikedRestaurantItemBinding
import com.renanrhoden.wheretolunch.feature.restaurantlisting.RestaurantListingAdapter.ViewHolder

class RestaurantListingAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private val inflater by lazy {
        LayoutInflater.from(context)
    }

    var list = listOf<Restaurant>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.liked_restaurant_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(private val binding: LikedRestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.restaurant = restaurant
        }
    }
}
