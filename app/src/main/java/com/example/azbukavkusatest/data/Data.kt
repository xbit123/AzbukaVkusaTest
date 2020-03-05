package com.example.azbukavkusatest.data

import com.example.azbukavkusatest.entity.CategoryEntity
import com.example.azbukavkusatest.utils.Constants

object Data {
    lateinit var categories: Map<Int, ArrayList<CategoryEntity>>
        private set

    private lateinit var indices: List<Int>
    var initialized = false

    fun setCategories(newCategories: Map<Int, ArrayList<CategoryEntity>>) {
        if (!initialized) {
            //Filter out categories that aren't in our language
            categories = newCategories.filter { category ->
                category.value.find {
                    !blacklist.contains(it.categoryId) && it.languageId == Constants.LANGUAGE_ID
                } != null
            }
            indices = categories.keys.toList().sorted()
            initialized = true
        }
    }

    fun getSize() = categories.size

    fun getCategoryByIndex(index: Int) =
        if (categories.size > index)
            categories[indices[index]]?.find { it.languageId == Constants.LANGUAGE_ID }
        else null

    //this should come from the server though
    private val blacklist = listOf(17, 59, 60, 61, 62, 63)
}