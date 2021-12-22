package com.example.speedcarv2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.speedcarv2.databinding.AccountActivityBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.account_activity.*

class AccountActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: AccountActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AccountActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menu Hamburguer
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.contaUsuario -> navigateToAccount()

                R.id.viagens -> navigateToViagens()

                R.id.pagamentos -> Toast.makeText(applicationContext, "Clicou em Pagamentos",
                    Toast.LENGTH_SHORT).show()

                R.id.logout -> logout()
            }
            true
        }


        binding.btnVoltarIP.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
            startActivity(homeActivity)
        }

        binding.btnEditarInfoIP.setOnClickListener {
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

                TextUtils.isEmpty(binding.edtEmailIP.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@AccountActivity,
                        "Insira seu email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.edtSenhaIP.text.toString().trim { it <= ' ' }) -> {
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

                    val email: String = binding.edtEmailIP.text.toString().trim { it <= ' ' }
                    var senha: String = binding.edtSenhaIP.text.toString().trim { it <= ' ' }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@AccountActivity, LoginActivity::class.java));
        finish();
    }

    fun navigateToAccount() {
        val accountActivity = Intent(this, AccountActivity::class.java);
        startActivity(accountActivity)
    }

    fun navigateToViagens() {
        val viagensActivity = Intent(this, ViagensActivity::class.java);
        startActivity(viagensActivity)
    }
}