package com.example.speedcarv2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.speedcarv2.databinding.ActivityEditAccountBinding
import com.example.speedcarv2.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditAccountActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityEditAccountBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
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

        // Persistência dos dados
        binding.btnSalvarInfoEdtIP.setOnClickListener {

            val nomeCompleto = binding.edtNomeCompletoEdtIP.text.toString()
            val dtNascimento = binding.edtDtNascimentoEdtIP.text.toString()
            val cpf = binding.edtCpfEdtIP.text.toString()
            val telefone = binding.edtTelefoneEdtIP.text.toString()
            val endereco = binding.edtEnderecoEdtIP.text.toString()
            val modeloVeiculo = binding.edtModeloVeiculoEdtIP.text.toString()
            val placaVeiculo = binding.edtPlacaVeiculoEdtIP.text.toString()
            val corVeiculo = binding.edtCorVeiculoEdtIP.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val user = UserModel(nomeCompleto, dtNascimento, cpf,
                telefone, endereco, modeloVeiculo,
                corVeiculo, placaVeiculo)

            database.child(nomeCompleto).setValue(user).addOnSuccessListener {
                Toast.makeText(this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar as informações!", Toast.LENGTH_SHORT).show()
            }
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
        val accountActivity = Intent(this, AccountActivity::class.java);
        startActivity(accountActivity)
    }

    fun navigateToViagens() {
        val viagensActivity = Intent(this, ViagensActivity::class.java);
        startActivity(viagensActivity)
    }
}