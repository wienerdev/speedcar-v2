package com.preko.speedcarv2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.preko.speedcarv2.R
import kotlinx.android.synthetic.main.activity_rec_senha.*

class RecSenhaActivity : AppCompatActivity() {

    private val TAG = "RecPasswordActivity"

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rec_senha)

        inicializeRecovery()
    }

    private fun inicializeRecovery() {
        mAuth = FirebaseAuth.getInstance()

        btnEnviarRS!!.setOnClickListener {
            sendPasswordEmail()
        }
    }

    private fun sendPasswordEmail() {
        val email = edtEmailRS?.text.toString()

        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val message = "Email enviado!"
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
                        task.exception!!.message?.let { Log.w(TAG, it) }
                        Toast.makeText(this, "Email não encontrado!", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Digite um email válido!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
    val intent = Intent(this@RecSenhaActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}