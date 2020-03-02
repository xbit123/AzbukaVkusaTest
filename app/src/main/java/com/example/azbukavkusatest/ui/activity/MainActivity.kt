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

class MainActivity : AppCompatActivity(), MainViewModel.Initrvinterface {
    lateinit var adapter: CategoriesAdapter
    private val viewModel : MainViewModel by viewModels()
    lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Categories.isInitialized()) viewModel.requestCategories(this)
    }

    override fun initRV() {
        runOnUiThread {
            rvMain = findViewById(R.id.rv_main)
            val linearLayoutManager = LinearLayoutManager(this)
            rvMain.layoutManager = linearLayoutManager
            rvMain.setHasFixedSize(true)
            adapter = CategoriesAdapter()
            rvMain.adapter = adapter
            viewModel.getCategories().observe(this, Observer { adapter.submitList(it) })
        }
    }
}
