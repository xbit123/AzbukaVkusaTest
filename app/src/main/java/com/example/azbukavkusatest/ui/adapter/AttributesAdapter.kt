package com.example.azbukavkusatest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.azbukavkusatest.R
import com.example.azbukavkusatest.entity.AttributesEntity
import kotlinx.android.synthetic.main.view_holder_product_attributes.view.*

class AttributesAdapter(val attributes: List<AttributesEntity>) : RecyclerView.Adapter<AttributesAdapter.AttributesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_holder_product_attributes, parent, false)
        return AttributesViewHolder(view)
    }

    override fun getItemCount() = attributes.size

    override fun onBindViewHolder(holder: AttributesViewHolder, position: Int) {
        holder.bindAttribute(attributes[position])
    }

    class AttributesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindAttribute(attribute: AttributesEntity) {
            itemView.tv_attribute_name.text = attribute.name
            itemView.tv_attribute_value.text = attribute.text
        }
    }
}