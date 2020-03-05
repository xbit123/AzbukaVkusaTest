package com.example.azbukavkusatest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.azbukavkusatest.ui.adapter.CategoriesAdapter
import com.example.azbukavkusatest.ui.viewmodel.MainViewModel
import com.example.azbukavkusatest.R
import com.example.azbukavkusatest.api.State
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var categoriesAdapter: CategoriesAdapter
    private val viewModel: MainViewModel by viewModels()
    lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_retry.setOnClickListener {
            viewModel.retry()
        }

        viewModel.categoriesLoadedLd.observe(this, Observer {
            if (it) initMainRv()
        })

        viewModel.stateLd.observe(this, Observer {
            when (it) {
                State.DONE -> { hidePb(); hideError() }
                State.ERROR -> { hidePb(); showError() }
                State.LOADING -> { showPb(); hideError() }
            }
        })
    }

    private fun showPb() {
        pb_loading.visibility = View.VISIBLE
    }

    private fun hidePb() {
        pb_loading.visibility = View.GONE
    }

    private fun hideError() {
        cl_retry.visibility = View.GONE
        rv_main.visibility = View.VISIBLE
    }

    private fun showError() {
        cl_retry.visibility = View.VISIBLE
        rv_main.visibility = View.GONE
    }

    private fun initMainRv() {
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
