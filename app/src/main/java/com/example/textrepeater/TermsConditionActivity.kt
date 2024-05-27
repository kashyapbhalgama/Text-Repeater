package com.example.textrepeater

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.textrepeater.databinding.ActivityTermsConditionBinding

class TermsConditionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsConditionBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTermsConditionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.termsConditionsCustomBar.TopText.text = "Terms & Conditions"
        binding.termsConditionsCustomBar.TopBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}