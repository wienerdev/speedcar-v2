package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userId = intent.getStringExtra("user_id");
        val emailId = intent.getStringExtra("email_id");
        val btLogout = findViewById<Button>(R.id.btnLogout);

        btLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@HomeActivity, LoginActivity::class.java));
            finish();
        }

    }
}