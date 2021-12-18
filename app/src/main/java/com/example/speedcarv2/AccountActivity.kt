package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val edNomeCompleto = findViewById<EditText>(R.id.edtNomeCompleto)
        val edDtNascimento = findViewById<EditText>(R.id.edtDtNascimento)
        val edCpf = findViewById<EditText>(R.id.edtCpf)
        val edTelefone = findViewById<EditText>(R.id.edtTelefone)
        val edEndereco = findViewById<EditText>(R.id.edtEndereco)
        val edModeloVeiculo = findViewById<EditText>(R.id.edtModeloVeiculo)
        val edPlacaVeiculo = findViewById<EditText>(R.id.edtPlacaVeiculo)
        val edCorVeiculo = findViewById<EditText>(R.id.edtCorVeiculo)
        val edEmail = findViewById<EditText>(R.id.edtEmail)
        val edSenha = findViewById<EditText>(R.id.edtSenha)
        val edSenhaConfirmada = findViewById<EditText>(R.id.edtSenhaConfirmada)


        btRegistrar.setOnClickListener {
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

                TextUtils.isEmpty(edEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@AccountActivity,
                        "Insira seu email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(edSenha.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@AccountActivity,
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

                    val email: String = edEmail.text.toString().trim { it <= ' ' }
                    var senha: String = edSenha.text.toString().trim { it <= ' ' }

                    // Create an instance and create a register user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@AccountActivity,
                                        "Você foi registrado com sucesso!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@AccountActivity, HomeActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent);
                                    finish()

                                } else {
                                    Toast.makeText(
                                        this@AccountActivity,
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