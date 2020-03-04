package com.example.azbukavkusatest

import com.example.azbukavkusatest.entity.CategoryEntity
import com.example.azbukavkusatest.utils.Constants

object Categories {
    private lateinit var categories: Map<Int, ArrayList<CategoryEntity>>
    private lateinit var indices: List<Int>

    fun setNewCategories(newCategories: Map<Int, ArrayList<CategoryEntity>>) {
        //Filter out categories that aren't in our language
        categories = newCategories.filter { category ->
            category.value.find { it.languageId == Constants.LANGUAGE_ID } != null
        }
        indices = categories.keys.toList().sorted()
    }

    fun getCategories() = categories

    fun getSize() = categories.size

    fun getCategoryByIndex(index: Int) =
        if (categories.size > index)
            categories[indices[index]]?.find { it.languageId == Constants.LANGUAGE_ID }
        else null

    fun isInitialized() = ::categories.isInitialized
}