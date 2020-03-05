package com.example.azbukavkusatest.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.example.azbukavkusatest.api.ShopService.Companion.shopService
import com.example.azbukavkusatest.api.State
import com.example.azbukavkusatest.entity.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class CategoriesDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val stateLd: MutableLiveData<State>)
    : PositionalDataSource<CategoryEntity>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<CategoryEntity>) {
        Log.d("loadRange", "${params.loadSize} ${params.startPosition}")

        val indexStart = params.startPosition
        val count = minOf(Data.getSize() - params.startPosition, params.loadSize)

        //requests multiple categories at once and updates their product lists
        compositeDisposable.add(
            Observable
                .range(indexStart, count)
                .flatMap { Observable.just(Data.getCategoryByIndex(it)) }
                .flatMap { category ->
                    shopService.getProductsByCategory(category.categoryId)
                        .observeOn(Schedulers.io()).toObservable()
                }
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                    val resultList = mutableListOf<CategoryEntity>()
                    for (index in 0 until count) {
                        val category = Data.getCategoryByIndex(index + indexStart)!!
                        category.products = it[index].data
                        resultList.add(category)
                    }
                    callback.onResult(resultList)
                    },
                    {
                        stateLd.postValue(State.ERROR)
                    })
            )

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<CategoryEntity>) {
        Log.d("loadInitial", "${params.pageSize} ${params.requestedLoadSize} ${params.requestedStartPosition}")

        val indexStart = params.requestedStartPosition
        val count = minOf(Data.getSize() - params.requestedStartPosition, params.pageSize)

        //requests multiple categories at once and updates their product lists
        compositeDisposable.add(
            Observable
                .range(indexStart, count)
                .flatMap { Observable.just(Data.getCategoryByIndex(it)) }
                .flatMap { category ->
                    shopService.getProductsByCategory(category.categoryId)
                        .observeOn(Schedulers.io()).toObservable()
                }
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                    val resultList = mutableListOf<CategoryEntity>()
                    for (index in 0 until count) {
                        val category = Data.getCategoryByIndex(index + indexStart)!!
                        category.products = it[index].data
                        resultList.add(category)
                    }
                    callback.onResult(resultList, 0)
                    },
                    {
                        stateLd.postValue(State.ERROR)
                    })
        )
    }
}