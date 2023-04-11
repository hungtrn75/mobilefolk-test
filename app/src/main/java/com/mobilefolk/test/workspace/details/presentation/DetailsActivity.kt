package com.mobilefolk.test.workspace.details.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat.*
import com.mobilefolk.test.databinding.ActivityDetailsBinding
import com.mobilefolk.test.workspace.main.domain.models.CatImage


class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingViews(savedInstanceState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun settingViews(savedInstanceState: Bundle?) {
        val imageExtra = intent.getParcelableExtra<CatImage>("image")
        imageExtra?.apply {

        }
    }
}