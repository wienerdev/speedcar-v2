package com.example.speedcarv2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedcarv2.databinding.ActivitySignInBinding
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
                /*
                TextUtils.isEmpty(edNomeCompleto.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira seu nome completo!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edDtNascimento.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira sua data de nascimento!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edCpf.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira seu CPF!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edTelefone.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira seu telefone com DDD!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edEndereco.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira seu endereço!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edModeloVeiculo.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira o modelo do seu veículo!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edPlacaVeiculo.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira a placa do seu veículo!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edCorVeiculo.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Insira a cor do seu veículo!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                 */

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

                /*
                TextUtils.isEmpty(edSenhaConfirmada.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@SignInActivity,
                        "Confirme a sua senha!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                 */
                else -> {

                    val email: String = binding.edtEmailRC.text.toString().trim { it <= ' ' }
                    var senha: String = binding.edtSenhaRC.text.toString().trim { it <= ' ' }

                    // Create an instance and create a register user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                               if (task.isSuccessful) {
                                   val firebaseUser: FirebaseUser = task.result!!.user!!

                                   Toast.makeText(
                                       this@SignInActivity,
                                       "Você foi registrado com sucesso!",
                                       Toast.LENGTH_SHORT
                                   ).show()

                                   val intent =
                                       Intent(this@SignInActivity, HomeActivity::class.java)
                                   intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
                }
            }
        }
    }
}