package com.example.azbukavkusatest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.azbukavkusatest.Constants.Companion.PRODUCT_JSON_TAG
import com.example.azbukavkusatest.entity.ProductEntity
import com.example.azbukavkusatest.ui.activity.ProductActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.view_holder_product.view.*

class ProductsAdapter(val products: List<ProductEntity>): RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindProduct(product: ProductEntity) {
            itemView.tv_vh_product_manufacturer.text = product.manufacturer
            itemView.tv_vh_product_model.text = product.model
            itemView.tv_vh_product_price.text = product.priceFormatted
            Glide.
                with(itemView.context).
                load(product.image).
                centerCrop().
                into(itemView.iv_vh_product_icon)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ProductActivity::class.java)
                val productJson = Gson().toJson(product)
                intent.putExtra(PRODUCT_JSON_TAG, productJson)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                itemView.context.startActivity(intent)
            }
        }
    }
}