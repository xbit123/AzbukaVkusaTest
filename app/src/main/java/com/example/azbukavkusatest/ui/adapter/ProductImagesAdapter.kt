package com.example.azbukavkusatest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.azbukavkusatest.R
import kotlinx.android.synthetic.main.view_holder_product_image.view.*

class ProductImagesAdapter(val images: List<String>, val selectedImageLd: MutableLiveData<String>)
    : RecyclerView.Adapter<ProductImagesAdapter.ProductImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_product_image, parent, false)
        return ProductImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        holder.bindImage(images[position], selectedImageLd)
    }

    class ProductImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindImage(imageUrl: String, selectedImageLd: MutableLiveData<String>) {
            Glide
                .with(itemView.context)
                .load(imageUrl)
                .centerCrop()
                .error(R.drawable.no_image)
                .into(itemView.iv_vh_product_image)
            itemView.setOnClickListener {
                selectedImageLd.postValue(imageUrl)
            }
        }
    }
}