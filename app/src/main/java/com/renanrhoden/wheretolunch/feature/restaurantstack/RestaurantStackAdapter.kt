package com.renanrhoden.wheretolunch.feature.restaurantstack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.renanrhoden.wheretolunch.R
import com.renanrhoden.wheretolunch.databinding.RestaurantCardBinding

class RestaurantStackAdapter : Adapter<ViewHolder>() {

    var list = listOf<RestaurantCardViewModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                R.layout.restaurant_card,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class ViewHolder(private val binding: RestaurantCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: RestaurantCardViewModel) {
        binding.viewModel = viewModel
    }
}