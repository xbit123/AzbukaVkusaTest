package com.example.azbukavkusatest.entity

import com.google.gson.annotations.SerializedName

class ApiResponse <T> (
    @SerializedName("success")
    var success: Int,
    @SerializedName("error")
    var error: ArrayList<String>,
    @SerializedName("data")
    var data: T
)