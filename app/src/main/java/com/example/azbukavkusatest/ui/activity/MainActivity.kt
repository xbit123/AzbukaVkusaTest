package com.example.azbukavkusatest.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.azbukavkusatest.Categories
import com.example.azbukavkusatest.CategoriesAdapter
import com.example.azbukavkusatest.ProductsAdapter
import com.example.azbukavkusatest.ui.viewmodel.MainViewModel
import com.example.azbukavkusatest.R
import com.example.azbukavkusatest.entity.ProductEntity

class MainActivity : AppCompatActivity() {
    lateinit var categoriesAdapter: CategoriesAdapter
    private val viewModel: MainViewModel by viewModels()
    lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Categories.isInitialized()) viewModel.requestCategories()
        viewModel.getCategoriesLoadedLiveData().observe(this, Observer {
            if (it) initRV()
        })
    }

    private fun initRV() {
        val linearLayoutManager = LinearLayoutManager(this)
        categoriesAdapter = CategoriesAdapter()
        rvMain = findViewById<RecyclerView>(R.id.rv_main).apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            this.adapter = categoriesAdapter
        }
        viewModel.getCategories().observe(this, Observer { categoriesAdapter.submitList(it) })
    }
}
