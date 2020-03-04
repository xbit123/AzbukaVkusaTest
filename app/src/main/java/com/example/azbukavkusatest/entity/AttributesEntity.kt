package com.example.azbukavkusatest.entity

import com.google.gson.annotations.SerializedName

data class AttributesEntity (
    @SerializedName("attribute_id")
    var attributeId: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("text")
    var text: String,
    @SerializedName("language_id")
    var languageId: Int
)