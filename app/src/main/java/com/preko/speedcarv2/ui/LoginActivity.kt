package com.preko.speedcarv2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.preko.speedcarv2.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignInLG.setOnClickListener {
            navigateSignIn()
        }

        binding.btnForgetPassLG.setOnClickListener {
            naviageToRecSenha()
        }

        binding.btnLoginLG.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.edtEmailLG.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Insira seu email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.edtSenhaLG.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Insira sua senha!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = binding.edtEmailLG.text.toString().trim { it <= ' ' }
                    var senha: String = binding.edtSenhaLG.text.toString().trim { it <= ' ' }

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

    private fun naviageToRecSenha() {
        val recSenha = Intent(this, RecSenhaActivity::class.java);
        startActivity(recSenha)    }

    private fun navigateSignIn() {
        val signInPage = Intent(this, SignInActivity::class.java);
        startActivity(signInPage)
    }

}