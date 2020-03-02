package com.example.azbukavkusatest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.azbukavkusatest.Constants.Companion.PRODUCT_JSON_TAG
import com.example.azbukavkusatest.R
import com.example.azbukavkusatest.databinding.ActivityProductBinding
import com.example.azbukavkusatest.entity.ProductEntity
import com.google.gson.Gson

class ProductActivity : AppCompatActivity() {
    lateinit var product: ProductEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProductBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_product)

        if (intent == null || intent.extras == null) finish()

        val productJson = intent?.extras?.getString(PRODUCT_JSON_TAG)
        product = Gson().fromJson(productJson, ProductEntity::class.java)

        binding.product = product
    }
}
