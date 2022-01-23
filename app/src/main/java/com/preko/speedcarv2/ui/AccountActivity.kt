package com.preko.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.AccountActivityBinding
import com.preko.speedcarv2.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.account_activity.*

class AccountActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: AccountActivityBinding
    private lateinit var database: DatabaseReference
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AccountActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (uid != null) {
            readData(uid)
        }

        // Menu Hamburguer
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeApp -> navigateToHome()

                R.id.contaUsuario -> navigateToAccount()

                R.id.viagens -> navigateToViagens()

                R.id.pagamentos -> navigateToPagamento()

                R.id.logout -> logout()
            }
            true
        }

        binding.btnEditarInfoIP.setOnClickListener {
            val editAccountActivity = Intent(this, com.preko.speedcarv2.ui.EditAccountActivity::class.java)
            startActivity(editAccountActivity)
        }

        binding.btnVoltarIP.setOnClickListener {
            val homeActivity = Intent(this, com.preko.speedcarv2.ui.HomeActivity::class.java)
            startActivity(homeActivity)
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
        startActivity(Intent(this@AccountActivity, com.preko.speedcarv2.ui.LoginActivity::class.java));
        finish();
    }

    fun navigateToAccount() {
        val accountActivity = Intent(this, com.preko.speedcarv2.ui.AccountActivity::class.java);
        startActivity(accountActivity)
    }

    fun navigateToViagens() {
        val viagensActivity = Intent(this, com.preko.speedcarv2.ui.ViagensActivity::class.java);
        startActivity(viagensActivity)
    }

    fun navigateToHome() {
        val homeActivity = Intent(this, com.preko.speedcarv2.ui.HomeActivity::class.java)
        startActivity(homeActivity)
    }

    fun navigateToPagamento() {
        val pagamentoActivity = Intent(this, com.preko.speedcarv2.ui.PagamentoActivity::class.java)
        startActivity(pagamentoActivity)
    }

    fun readData(uid: String) {

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        database = FirebaseDatabase.getInstance().getReference("Users")

        if (uid != null) {
            database.child(uid).get().addOnSuccessListener {

                val nomeCompleto = it.child("nomeCompleto").value
                val dtNascimento = it.child("dtNascimento").value
                val cpf = it.child("cpf").value
                val telefone = it.child("telefone").value
                val endereco = it.child("endereco").value
                val modeloVeiculo = it.child("modeloVeiculo").value
                val corVeiculo = it.child("corVeiculo").value
                val placaVeiculo = it.child("placaVeiculo").value
                val email = FirebaseAuth.getInstance().currentUser?.email

                if (nomeCompleto != null) {
                    edtNomeCompletoIP.setText(nomeCompleto.toString())
                }
                if (dtNascimento != null) {
                    edtDtNascimentoIP.setText(dtNascimento.toString())
                }
                if (cpf != null) {
                    edtCpfIP.setText(cpf.toString())
                }
                if (telefone != null) {
                    edtTelefoneIP.setText(telefone.toString())
                }
                if (endereco != null) {
                    edtEnderecoIP.setText(endereco.toString())
                }
                if (modeloVeiculo != null) {
                    edtModeloVeiculoIP.setText(modeloVeiculo.toString())
                }
                if (corVeiculo != null) {
                    edtCorVeiculoIP.setText(corVeiculo.toString())
                }
                if (placaVeiculo != null) {
                    edtPlacaVeiculoIP.setText(placaVeiculo.toString())
                }
                edtEmailIP.setText(email.toString())

            }
        } else {
            Toast.makeText(this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show()
        }
    }

}