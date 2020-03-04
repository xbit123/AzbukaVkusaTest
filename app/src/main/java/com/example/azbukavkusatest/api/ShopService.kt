package com.example.azbukavkusatest.api

import com.example.azbukavkusatest.entity.ApiResponse
import com.example.azbukavkusatest.entity.CategoryEntity
import com.example.azbukavkusatest.entity.ProductEntity
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://opencart3-simple.api.opencart-api.com/api/rest_admin/"
private const val HEADER_NAME = "X-Oc-Restadmin-Id"
private const val HEADER_VALUE = "123"

interface ShopService {

    @GET("categories")
    fun getCategories() : Call<ApiResponse<Map<Int, ArrayList<CategoryEntity>>>>

    @GET("products/category/{category_id}")
    fun getProductsByCategory(
        @Path("category_id") categoryId: Int
    ) : Single<ApiResponse<List<ProductEntity>>>

    companion object {
        private val okHttpClient = OkHttpClient.Builder().addInterceptor {
            val request = it.request().newBuilder().addHeader(HEADER_NAME, HEADER_VALUE).build()
            it.proceed(request)
        }.build()

        val shopService: ShopService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopService::class.java)
    }
}