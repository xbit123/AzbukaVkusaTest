package com.example.azbukavkusatest

import androidx.recyclerview.widget.DiffUtil
import com.example.azbukavkusatest.entity.CategoryEntity

class DiffUtilCallBack : DiffUtil.ItemCallback<CategoryEntity>() {
    override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem.categoryId == newItem.categoryId
    }

    override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem == newItem
    }
}