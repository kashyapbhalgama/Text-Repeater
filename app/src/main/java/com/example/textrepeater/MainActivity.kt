@file:Suppress("DEPRECATION")

package com.example.textrepeater

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.textrepeater.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n", "QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                R.id.nav_about -> {
                    startActivity(Intent(this, AboutUsActivity::class.java))
                }

                R.id.nav_privacy -> {
                    startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                }

                R.id.nav_terms -> {
                    startActivity(Intent(this, TermsConditionActivity::class.java))
                }

                R.id.nav_share -> {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Text Repeater")
                    shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out this amazing Text Repeater app: https://play.google.com/store/apps"
                    )
                    startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
            }
            true
        }

        binding.generateButton.setOnClickListener {
            val mProgressDialog = ProgressDialog(this)
            mProgressDialog.setMessage("Please Wait..")
            mProgressDialog.show()

            CoroutineScope(Dispatchers.Main).launch {
                val text = binding.inputText.text.toString()
                val repetitions = binding.inputNumber.text.toString().toIntOrNull()
                val repeatInNewLines = binding.newLineCheckBox.isChecked

                val repeatedText = withContext(Dispatchers.Default) {
                    if (repetitions != null && repetitions > 0) {
                        if (repeatInNewLines) {
                            (1..repetitions).joinToString("\n") { text }
                        } else {
                            text.repeat(repetitions)
                        }
                    } else {
                        null
                    }
                }

                if (repeatedText != null) {
                    binding.outputText.text = repeatedText
                } else {
                    binding.outputText.text = "Please enter a valid number of repetitions."
                }
                mProgressDialog.dismiss()
            }
            hideKeyboard()
        }

        binding.copyButton.setOnClickListener {
            val textToCopy = binding.outputText.text.toString()
            if (textToCopy.isNotEmpty()) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("repeated text", textToCopy)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nothing to copy", Toast.LENGTH_SHORT).show()
            }
        }

        binding.shareButton.setOnClickListener {
            val textToShare = binding.outputText.text.toString()
            if (textToShare.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    setPackage("com.whatsapp")
                    putExtra(Intent.EXTRA_TEXT, textToShare)
                }
                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Nothing to share", Toast.LENGTH_SHORT).show()
            }
        }

        binding.clearButton.setOnClickListener {
            binding.inputText.setText("")
            binding.inputNumber.setText("")
            binding.outputText.text = ""
        }

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
