package com.example.azbukavkusatest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.example.azbukavkusatest.api.ShopService.Companion.shopService
import com.example.azbukavkusatest.api.State
import com.example.azbukavkusatest.entity.CategoryEntity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class CategoriesDataSource(private val compositeDisposable: CompositeDisposable)
    : PositionalDataSource<CategoryEntity>() {
    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CategoryEntity>) {
        Log.d("loadRange", "${params.loadSize} ${params.startPosition}")

        val category = Categories.getCategoryByIndex(params.startPosition)
        if (category == null) {
            callback.onResult(listOf())
            return
        }

        compositeDisposable.add(
            shopService.getProductsByCategory(category!!.categoryId)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        category.products = response.data
                        callback.onResult(listOf(category))
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadRange(params, callback) })
                    }
                )
        )
        /*if (category == null) {
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
        }*/
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CategoryEntity>) {
        Log.d("loadInitial", "${params.pageSize} ${params.requestedLoadSize} ${params.requestedStartPosition}")

        val category = Categories.getCategoryByIndex(params.requestedStartPosition)
        if (category == null) {
            callback.onResult(listOf(), 0)
            return
        }

        compositeDisposable.add(
            shopService.getProductsByCategory(category!!.categoryId)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        category.products = response.data
                        callback.onResult(listOf(category), 0)
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )

        /*if (category == null) {
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
        }*/

        //callback.onResult(arrayListOf(Categories.getCategoryByIndex(0)), 0)
    }

    fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}