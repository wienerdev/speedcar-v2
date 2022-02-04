package com.preko.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityEditAccountBinding
import com.preko.speedcarv2.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.account_activity.*
import kotlinx.android.synthetic.main.activity_edit_account.*

class EditAccountActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityEditAccountBinding
    private lateinit var database: DatabaseReference
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (uid != null) {
            readUserData(uid)
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

        // Persistência dos dados
        binding.btnSalvarInfoEdtIP.setOnClickListener {

            val telefone = binding.edtTelefoneEdtIP.text.toString()
            val endereco = binding.edtEnderecoEdtIP.text.toString()
            val modeloVeiculo = binding.edtModeloVeiculoEdtIP.text.toString()
            val placaVeiculo = binding.edtPlacaVeiculoEdtIP.text.toString()
            val corVeiculo = binding.edtCorVeiculoEdtIP.text.toString()

            val user = com.preko.speedcarv2.model.UserModel(
                telefone, endereco, modeloVeiculo,
                corVeiculo, placaVeiculo
            )

            val uid = FirebaseAuth.getInstance().currentUser?.uid

            if (uid != null) {
                FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(uid)
                    .setValue(user)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Erro ao salvar as informações!", Toast.LENGTH_SHORT).show()
                    }
            }
            navigateToAccount()
        }

        binding.btnVoltarEdtIP.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
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
        startActivity(Intent(this@EditAccountActivity, LoginActivity::class.java));
        finish();
    }

    fun navigateToAccount() {
        val accountActivity = Intent(this, com.preko.speedcarv2.ui.AccountActivity::class.java);
        startActivity(accountActivity)
    }

    fun navigateToViagens() {
        val viagensActivity = Intent(this, ViagensActivity::class.java);
        startActivity(viagensActivity)
    }

    fun navigateToHome() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        startActivity(homeActivity)
    }

    fun navigateToPagamento() {
        val pagamentoActivity = Intent(this, PagamentoActivity::class.java)
        startActivity(pagamentoActivity)
    }

    fun readUserData(uid: String) {

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

                edtNomeCompletoEdtIP.setText(nomeCompleto.toString())
                edtDtNascimentoEdtIP.setText(dtNascimento.toString())
                edtCpfEdtIP.setText(cpf.toString())
                edtTelefoneEdtIP.setText(telefone.toString())
                edtEnderecoEdtIP.setText(endereco.toString())
                edtModeloVeiculoEdtIP.setText(modeloVeiculo.toString())
                edtCorVeiculoEdtIP.setText(corVeiculo.toString())
                edtPlacaVeiculoEdtIP.setText(placaVeiculo.toString())
                edtEmailEdtIP.setText(email.toString())

            }
        } else {
            Toast.makeText(this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show()
        }
    }
}