package com.mtsapps.eteration.data.remote.entity

import com.google.gson.annotations.SerializedName
import com.mtsapps.eteration.domain.models.Product

data class ProductResponseEntity (

    @SerializedName("createdAt"   ) var createdAt   : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("image"       ) var image       : String? = null,
    @SerializedName("price"       ) var price       : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("model"       ) var model       : String? = null,
    @SerializedName("brand"       ) var brand       : String? = null,
    @SerializedName("id"          ) var id          : String? = null

){
    fun toProduct():Product{
       return Product(createdAt, name, image, price, description, model, brand, id)

    }
}