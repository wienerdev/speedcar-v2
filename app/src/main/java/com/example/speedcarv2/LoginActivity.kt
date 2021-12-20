package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login)

        val btLogin = findViewById<Button>(R.id.btnLogin)
        val btSignIn = findViewById<Button>(R.id.btnSignIn)
        val edEmail = findViewById<EditText>(R.id.edtEmail)
        val edSenha = findViewById<EditText>(R.id.edtSenha)

        btSignIn.setOnClickListener {
            navigateSignIn()
        }

        btLogin.setOnClickListener {
            when {
                TextUtils.isEmpty(edEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Insira seu email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edSenha.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Insira sua senha!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = edEmail.text.toString().trim { it <= ' ' }
                    var senha: String = edSenha.text.toString().trim { it <= ' ' }

                    // Create an instance and create a register user with email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener { task ->

                                if (task.isSuccessful) {

                                    val intent =
                                        Intent(this@LoginActivity, HomeActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                }
            }
        }

    }

    private fun navigateSignIn() {
        val signInPage = Intent(this, SignInActivity::class.java);
        startActivity(signInPage)
    }

    private fun navigateHome() {

    }



}