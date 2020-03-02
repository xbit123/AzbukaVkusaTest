package com.example.azbukavkusatest.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class SpecialEntity (
    @SerializedName("customer_group_id")
    var customerGroupId: Int,
    @SerializedName("priority")
    var priority: Int,
    @SerializedName("price")
    var price: Double,
    @SerializedName("date_start")
    var dateStart: Date,
    @SerializedName("date_end")
    var dateEnd: Date
)