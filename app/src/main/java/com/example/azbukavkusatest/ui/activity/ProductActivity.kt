package com.example.azbukavkusatest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.azbukavkusatest.utils.Constants.Companion.PRODUCT_JSON_TAG
import com.example.azbukavkusatest.ui.adapter.ProductImagesAdapter
import com.example.azbukavkusatest.R
import com.example.azbukavkusatest.databinding.ActivityProductBinding
import com.example.azbukavkusatest.entity.ProductEntity
import com.example.azbukavkusatest.ui.adapter.AttributesAdapter
import com.example.azbukavkusatest.ui.viewmodel.ProductViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {
    lateinit var product: ProductEntity
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProductBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_product)

        //get product
        if (intent == null || intent.extras == null) finish()
        val productJson = intent?.extras?.getString(PRODUCT_JSON_TAG)
        product = Gson().fromJson(productJson, ProductEntity::class.java)

        //setup images RV
        binding.rvProductImages.apply {
            val completeProductImages = ArrayList(product.images)
            completeProductImages.add(0, product.image)
            adapter = ProductImagesAdapter(completeProductImages, viewModel.getSelectedImageLd())
            setHasFixedSize(true)
        }

        //setup attributes RV
        binding.rvAttributes.apply {
            product.attributesInLanguage()?.let {
                if (it.isNotEmpty()) {
                    tv_specifications.visibility = View.VISIBLE
                    rv_attributes.visibility = View.VISIBLE

                    adapter = AttributesAdapter(it)
                }
            }
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }

        //setup product image
        viewModel.getSelectedImageLd().observe(this, Observer {
            Glide
                .with(this)
                .load(it)
                .centerCrop()
                .error(R.drawable.no_image)
                .into(binding.ivProduct)
        })

        //setup toolbar
        setSupportActionBar(binding.tbProduct)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.product = product
    }
}
