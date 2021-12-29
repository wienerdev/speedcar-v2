package com.example.speedcarv2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.example.speedcarv2.databinding.AccountActivityBinding
import com.example.speedcarv2.model.UserModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.account_activity.*

class AccountActivity : AppCompatActivity() {

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
                R.id.contaUsuario -> navigateToAccount()

                R.id.viagens -> navigateToViagens()

                R.id.pagamentos -> Toast.makeText(applicationContext, "Clicou em Pagamentos",
                    Toast.LENGTH_SHORT).show()

                R.id.logout -> logout()
            }
            true
        }

        binding.btnEditarInfoIP.setOnClickListener {
            val editAccountActivity = Intent(this, EditAccountActivity::class.java)
            startActivity(editAccountActivity)
        }

        binding.btnVoltarIP.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java)
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

                edtNomeCompletoIP.setText(nomeCompleto.toString())
                edtDtNascimentoIP.setText(dtNascimento.toString())
                edtCpfIP.setText(cpf.toString())
                edtTelefoneIP.setText(telefone.toString())
                edtEnderecoIP.setText(endereco.toString())
                edtModeloVeiculoIP.setText(modeloVeiculo.toString())
                edtCorVeiculoIP.setText(corVeiculo.toString())
                edtPlacaVeiculoIP.setText(placaVeiculo.toString())
                edtEmailIP.setText(email.toString())

            }
        } else {
            Toast.makeText(this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show()
        }
    }

}