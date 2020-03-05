package com.example.azbukavkusatest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.azbukavkusatest.data.Data
import com.example.azbukavkusatest.data.CategoriesDataSource
import com.example.azbukavkusatest.api.ShopService.Companion.shopService
import com.example.azbukavkusatest.api.State
import com.example.azbukavkusatest.entity.ApiResponse
import com.example.azbukavkusatest.entity.CategoryEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val categoriesLd: LiveData<PagedList<CategoryEntity>>
    val categoriesLoadedLd = MutableLiveData<Boolean>()
    val stateLd = MutableLiveData<State>()
    private val compositeDisposable = CompositeDisposable()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(4)
            .setEnablePlaceholders(false)
            .build()
        categoriesLd = initializedPagedListBuilder(config, compositeDisposable).build()

        retry()
    }

    fun retry() {
        if (!Data.initialized) requestCategories()
        else {
            stateLd.postValue(State.DONE)
            categoriesLoadedLd.postValue(true)
        }
    }

    fun requestCategories() {
        stateLd.postValue(State.LOADING)
        compositeDisposable.add(
            shopService.getCategories().subscribeOn(Schedulers.io()).subscribe(
            {
                if (it.success == 1 && it.error.size == 0) {
                    Data.setCategories(it.data)
                    categoriesLoadedLd.postValue(true)
                    stateLd.postValue(State.DONE)
                }
            },
            {
                stateLd.postValue(State.ERROR)
            }))
    }

    fun getCategories() : LiveData<PagedList<CategoryEntity>> = categoriesLd

    private fun initializedPagedListBuilder(config: PagedList.Config, compositeDisposable: CompositeDisposable):
            LivePagedListBuilder<Int, CategoryEntity> {

        val dataSourceFactory = object : DataSource.Factory<Int, CategoryEntity>() {
            override fun create(): DataSource<Int, CategoryEntity> {
                return CategoriesDataSource(compositeDisposable, stateLd)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}