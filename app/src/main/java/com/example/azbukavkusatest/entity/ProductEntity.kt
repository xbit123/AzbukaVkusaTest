package com.example.azbukavkusatest.entity

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.azbukavkusatest.utils.Constants
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
    @SerializedName("product_attributes")
    var attributes: HashMap<String, HashMap<Int, HashMap<Int, AttributesEntity>>>
) {
    val fullName
        get() = "$manufacturer $model"

    //Converts html into string
    val formattedDescription: String
        get() = productDescriptions
            .find { it.languageId == Constants.LANGUAGE_ID }?.description
            .let { HtmlDecoder.decode(it) }
            .replace("[\n]+".toRegex(), "\n")
            .replace("\t".toRegex(), "")

    //filters attributes that are in out language
    fun attributesInLanguage(): List<AttributesEntity>? {
        return attributes["attributes"]
            ?.flatMap { it.value.values }
            ?.filter { it.languageId == Constants.LANGUAGE_ID }
    }
}

//For images data binding
@BindingAdapter("productImage")
fun loadImage(view: AppCompatImageView, url: String?) {
    Glide.with(view.context).load(url).error(R.drawable.no_image).into(view)
}