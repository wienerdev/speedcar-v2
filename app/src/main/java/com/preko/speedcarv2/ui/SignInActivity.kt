package com.preko.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.preko.speedcarv2.databinding.ActivitySignInBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrarRC.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.edtEmailRC.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira seu email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.edtSenhaRC.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira sua senha!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {

                    val email: String = binding.edtEmailRC.text.toString().trim { it <= ' ' }
                    var senha: String = binding.edtSenhaRC.text.toString().trim { it <= ' ' }
                    var senhaConfirmada: String = binding.edtSenhaConfirmadaRC.text.toString().trim { it <= ' ' }

                    // Create an instance and create a register user with email and password
                    if(senha.equals(senhaConfirmada)) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(
                                OnCompleteListener<AuthResult> { task ->

                                    if (task.isSuccessful) {
                                        val firebaseUser: FirebaseUser = task.result!!.user!!

                                        val intent =
                                            Intent(this@SignInActivity, RegInfoPessoalActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        intent.putExtra("user_id", firebaseUser.uid)
                                        intent.putExtra("email_id", email)
                                        startActivity(intent);
                                        finish()

                                    } else {
                                        Toast.makeText(
                                            this@SignInActivity,
                                            task.exception!!.message.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                    } else {
                        Toast.makeText(this, "A senha deve ser a mesma em ambos os campos!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}