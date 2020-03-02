package com.example.azbukavkusatest.entity

import com.google.gson.annotations.SerializedName

data class ProductDescriptionEntity (
    @SerializedName("language_id")
    var languageId: Int,
    @SerializedName("description")
    var description: String
)