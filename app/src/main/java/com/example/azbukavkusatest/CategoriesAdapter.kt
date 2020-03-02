package com.example.azbukavkusatest

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.azbukavkusatest.entity.CategoryEntity
import com.example.azbukavkusatest.utils.HtmlDecoder
import kotlinx.android.synthetic.main.view_holder_category.view.*

class CategoriesAdapter() :
    PagedListAdapter<CategoryEntity, CategoriesAdapter.CategoryViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bindCategory(it) }
    }

    class CategoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        lateinit var adapter : ProductsAdapter
        fun bindCategory(categoryEntity: CategoryEntity) {
            itemView.tv_category_name.text = HtmlDecoder.decode(categoryEntity.name)

            val gridLayoutManager = GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
            itemView.rv_products.setHasFixedSize(true)
            itemView.rv_products.layoutManager = gridLayoutManager
            adapter = ProductsAdapter(categoryEntity.products)
            itemView.rv_products.adapter = adapter
        }
    }
}