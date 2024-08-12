package com.mtsapps.eteration.presentation.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.eteration.R
import com.mtsapps.eteration.commons.utils.clickWithDebounce
import com.mtsapps.eteration.data.local.entity.CartEntity
import com.mtsapps.eteration.databinding.CartItemBinding

class CartAdapter(
    private val context: Context,
    private val minusOnClick: (CartEntity) -> Unit,
    private val plusOnClick: (CartEntity) -> Unit
) : ListAdapter<CartEntity, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartEntity = getItem(position)
        holder.bind(cartEntity)
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartEntity: CartEntity) {
            binding.apply {
                cartItemNameTitle.text = cartEntity.name
                cartItemPriceText.text = "${cartEntity.price}${context.getText(R.string.moneyIcon)}"
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