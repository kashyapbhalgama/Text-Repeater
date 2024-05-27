package com.example.textrepeater

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.textrepeater.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacyPolicyBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.privacyPolicyCustomBar.TopText.text = "Privacy & Policy"
        binding.privacyPolicyCustomBar.TopBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}