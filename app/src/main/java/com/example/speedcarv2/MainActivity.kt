package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        val btComecar = findViewById<Button>(R.id.btnComecar);

        btComecar.setOnClickListener {
            navigateLoginPage();
        }
    }

    private fun navigateLoginPage() {
        val loginPage = Intent(this, LoginActivity::class.java);
        startActivity(loginPage);
    }
}