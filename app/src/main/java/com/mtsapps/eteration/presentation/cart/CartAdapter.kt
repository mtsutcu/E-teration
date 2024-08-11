package com.mtsapps.eteration.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.eteration.commons.utils.clickWithDebounce
import com.mtsapps.eteration.data.local.entity.CartEntity
import com.mtsapps.eteration.databinding.CartItemBinding

class CartAdapter(
    private val minusOnClick: (CartEntity) -> Unit,
    private val plusOnClick: (CartEntity) -> Unit
) : ListAdapter<CartEntity, CartAdapter.ProductViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ProductViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartEntity: CartEntity) {
            binding.apply {
                cartItemNameTitle.text = cartEntity.name
                cartItemPriceText.text = cartEntity.price
                cartItemCountText.text = cartEntity.count.toString()
                cartItemMinusButton.clickWithDebounce {
                    minusOnClick.invoke(cartEntity)
                }
                cartItemPlusButton.clickWithDebounce {
                    plusOnClick.invoke(cartEntity)
                }
            }
        }
    }

    class CartDiffCallback : DiffUtil.ItemCallback<CartEntity>() {
        override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity): Boolean {
            return oldItem.count == newItem.count
        }
    }
}