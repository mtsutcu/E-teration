package com.mtsapps.eteration.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mtsapps.eteration.databinding.GridViewProductItemBinding
import com.mtsapps.eteration.domain.models.Product

class HomeProductsAdapter(private val context: Context
) : PagingDataAdapter<Product, HomeProductsAdapter.ProductViewHolder>(Product_COMPARATOR) {
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount && itemCount !=0){
            ITEM_VIEW_TYPE
        }else {
            LOADING_VIEW_TYPE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = GridViewProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        product?.let {
            holder.bind(it)
        }

    }

   inner class ProductViewHolder(private val binding: GridViewProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                productItemPrice.text = product.price
                productItemName.text = product.name
                Glide.with(context)
                    .load(product.image)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(productItemImage)
            }

        }
    }

    companion object {
        const val ITEM_VIEW_TYPE = 1
        const val LOADING_VIEW_TYPE = 2
        private val Product_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}