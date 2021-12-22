package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.speedcarv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnComecar.setOnClickListener {
            navigateLoginPage()
        }
    }

    private fun navigateLoginPage() {
        val loginPage = Intent(this, LoginActivity::class.java)
        startActivity(loginPage)
    }
}