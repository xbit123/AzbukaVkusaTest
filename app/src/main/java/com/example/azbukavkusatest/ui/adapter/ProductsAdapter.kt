package com.example.azbukavkusatest.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.azbukavkusatest.utils.Constants.Companion.PRODUCT_JSON_TAG
import com.example.azbukavkusatest.R
import com.example.azbukavkusatest.entity.ProductEntity
import com.example.azbukavkusatest.ui.activity.ProductActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.view_holder_product.view.*

class ProductsAdapter(val products: List<ProductEntity>): RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_product, parent, false)
        return ProductViewHolder(
            view
        )
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindProduct(product: ProductEntity) {
            itemView.apply {
                tv_vh_product_full_name.text = product.fullName
                tv_vh_product_price.text = product.priceFormatted
                Glide
                    .with(context)
                    .load(product.image)
                    .centerCrop()
                    .error(R.drawable.no_image)
                    .into(iv_vh_product_icon)

                setOnClickListener {
                    val intent = Intent(itemView.context, ProductActivity::class.java)
                    val productJson = Gson().toJson(product)
                    intent.putExtra(PRODUCT_JSON_TAG, productJson)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}