package com.example.azbukavkusatest.entity

import com.google.gson.annotations.SerializedName

data class CategoryEntity(
    @SerializedName("category_id")
    var categoryId: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("language_id")
    var languageId: Int,
    var products: List<ProductEntity>
)