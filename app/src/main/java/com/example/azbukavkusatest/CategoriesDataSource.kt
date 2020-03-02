package com.example.azbukavkusatest

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.azbukavkusatest.api.ShopService
import com.example.azbukavkusatest.entity.ApiResponse
import com.example.azbukavkusatest.entity.CategoryEntity
import com.example.azbukavkusatest.entity.ProductEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesDataSource : PositionalDataSource<CategoryEntity>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CategoryEntity>) {
        Log.d("loadRange", "${params.loadSize} ${params.startPosition}")

        val category = Categories.getCategoryByIndex(params.startPosition)

        if (category == null) {
            callback.onResult(emptyList())
        } else {
            ShopService.shopService.getProductsByCategory(category.categoryId).enqueue(object :
                Callback<ApiResponse<List<ProductEntity>>> {
                override fun onFailure(call: Call<ApiResponse<List<ProductEntity>>>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<ApiResponse<List<ProductEntity>>>,
                    response: Response<ApiResponse<List<ProductEntity>>>
                ) {
                    category.products = response.body()?.data!!
                    callback.onResult(arrayListOf(category))}
            })
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CategoryEntity>) {
        Log.d("loadInitial", "${params.pageSize} ${params.requestedLoadSize} ${params.requestedStartPosition}")

        val category = Categories.getCategoryByIndex(params.requestedStartPosition)

        if (category == null) {
            callback.onResult(emptyList(), 0)
        } else {
            ShopService.shopService.getProductsByCategory(category.categoryId).enqueue(object :
                Callback<ApiResponse<List<ProductEntity>>> {
                override fun onFailure(call: Call<ApiResponse<List<ProductEntity>>>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(
                    call: Call<ApiResponse<List<ProductEntity>>>,
                    response: Response<ApiResponse<List<ProductEntity>>>
                ) {
                    category.products = response.body()?.data!!
                    callback.onResult(arrayListOf(category), 0)}
            })
        }

        //callback.onResult(arrayListOf(Categories.getCategoryByIndex(0)), 0)
    }
}