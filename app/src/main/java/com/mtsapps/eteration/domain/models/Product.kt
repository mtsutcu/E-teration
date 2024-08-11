package com.mtsapps.eteration.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (

    @SerializedName("createdAt"   ) var createdAt   : String? = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("image"       ) var image       : String? = null,
    @SerializedName("price"       ) var price       : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("model"       ) var model       : String? = null,
    @SerializedName("brand"       ) var brand       : String? = null,
    @SerializedName("id"          ) var id          : String? = null

):Parcelable