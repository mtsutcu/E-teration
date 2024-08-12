package com.mtsapps.eteration.presentation.favourites


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.eteration.commons.utils.clickWithDebounce
import com.mtsapps.eteration.commons.utils.getImageFromUrl
import com.mtsapps.eteration.data.local.entity.FavoriteProduct
import com.mtsapps.eteration.databinding.FavouriteItemBinding

class FavouriteProductAdapter(
    private val onDeleteClick: (FavoriteProduct) -> Unit,

) : ListAdapter<FavoriteProduct, FavouriteProductAdapter.ViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteProduct = getItem(position)
        holder.bind(favoriteProduct)
    }

    inner class ViewHolder(private val binding: FavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteProduct: FavoriteProduct) {
            binding.apply {
                favouriteImage.getImageFromUrl(favoriteProduct.image)
                favouriteTitle.text = favoriteProduct.name
                favouritePrice.text = favoriteProduct.price
                favouriteDeleteIcon.clickWithDebounce {
                    onDeleteClick.invoke(favoriteProduct)
                }
            }
            }
        }
    }

    class CartDiffCallback : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem == newItem
        }
    }
