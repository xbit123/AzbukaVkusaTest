package com.example.azbukavkusatest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.azbukavkusatest.Categories
import com.example.azbukavkusatest.CategoriesDataSource
import com.example.azbukavkusatest.api.ShopService.Companion.shopService
import com.example.azbukavkusatest.entity.ApiResponse
import com.example.azbukavkusatest.entity.CategoryEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private lateinit var categoriesLiveData : LiveData<PagedList<CategoryEntity>>

    interface Initrvinterface {
        fun initRV()
    }

    fun requestCategories(initrvinterface: Initrvinterface) {
        shopService.getCategories().enqueue(object :
            Callback<ApiResponse<Map<Int, ArrayList<CategoryEntity>>>> {
            override fun onFailure(
                call: Call<ApiResponse<Map<Int, ArrayList<CategoryEntity>>>>,
                t: Throwable
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<ApiResponse<Map<Int, ArrayList<CategoryEntity>>>>,
                response: Response<ApiResponse<Map<Int, ArrayList<CategoryEntity>>>>
            ) {
                if (response.isSuccessful
                    && response.body() != null
                    && response.body()?.success == 1
                    && response.body()?.error?.size == 0
                    && response.body()?.data != null) {
                    Categories.setNewCategories(
                        response.body()?.data!!
                    )
                    initrvinterface.initRV()
                }
            }
        })

        val config = PagedList.Config.Builder()
            .setPageSize(1)
            .setPrefetchDistance(1)
            .setEnablePlaceholders(false)
            .build()
        categoriesLiveData = initializedPagedListBuilder(config).build()
    }

    fun getCategories() : LiveData<PagedList<CategoryEntity>> = categoriesLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, CategoryEntity> {

        val dataSourceFactory = object : DataSource.Factory<Int, CategoryEntity>() {
            override fun create(): DataSource<Int, CategoryEntity> {
                return CategoriesDataSource()
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }


}