package com.example.textrepeater

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.textrepeater.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutUsBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aboutUsCustomBar.TopText.text = "About Us"
        binding.aboutUsCustomBar.TopBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}