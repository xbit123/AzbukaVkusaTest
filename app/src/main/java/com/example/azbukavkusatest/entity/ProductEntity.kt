package com.example.azbukavkusatest.entity

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.azbukavkusatest.Constants
import com.example.azbukavkusatest.R
import com.example.azbukavkusatest.utils.HtmlDecoder
import com.google.gson.annotations.SerializedName

data class ProductEntity(
    @SerializedName("id")
    var id: Int,
    @SerializedName("manufacturer")
    var manufacturer: String,
    @SerializedName("model")
    var model: String,
    //100x100 images are too small
    @SerializedName("original_image")
    var image: String,
    @SerializedName("original_images")
    var images: ArrayList<String>,
    @SerializedName("price")
    var price: Double,
    @SerializedName("price_formated")
    var priceFormatted: String,
    @SerializedName("rating")
    var rating: Int,
    @SerializedName("product_description")
    var productDescriptions: ArrayList<ProductDescriptionEntity>,
    @SerializedName("attributes")
    var attributes: ArrayList<AttributesEntity>
) {
    fun getFormattedDescription(): String? {
        val desc =
            productDescriptions.find { it.languageId == Constants.LANGUAGE_ID }?.description
        return if (desc == null) null
        else HtmlDecoder.decode(desc)
    }
}

@BindingAdapter("productImage")
fun loadImage(view: AppCompatImageView, url: String?) {
    Glide.with(view.context).load(url).placeholder(R.drawable.no_image).into(view)
}