package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        val btLogin = findViewById<Button>(R.id.btnLogin);
        val btSignIn = findViewById<Button>(R.id.btnSignIn);

        btLogin.setOnClickListener {
            navigateHome();
        }

        btSignIn.setOnClickListener {
            navigateSignIn();
        }
    }

    private fun navigateSignIn() {
        val signInPage = Intent(this, SignInActivity::class.java);
        startActivity(signInPage);
    }

    private fun navigateHome() {

    }

}