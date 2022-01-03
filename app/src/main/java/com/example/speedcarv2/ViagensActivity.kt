package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.speedcarv2.databinding.ActivityViagensBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.account_activity.*
import kotlinx.android.synthetic.main.activity_viagens.*

class ViagensActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityViagensBinding
    private lateinit var database: DatabaseReference
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViagensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readData()

        binding.btnVoltarVGS.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
            startActivity(homeActivity)
        }

        binding.btnCriarViagemVGS.setOnClickListener {
            val criarViagemActivity = Intent(this, CriarViagemActivity::class.java);
            startActivity(criarViagemActivity)
        }

        binding.btnDetalhesViagemVGS.setOnClickListener {
            val detalhesViagemActivity = Intent(this, DetalhesViagemActivity::class.java);
            startActivity(detalhesViagemActivity)
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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@ViagensActivity, LoginActivity::class.java));
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

    fun readData() {

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        database = FirebaseDatabase.getInstance().getReference("Trips")

        if (uid != null) {
            database.child(uid).get().addOnSuccessListener {

                val regiao = it.child("uid").value
                val origem = it.child("origem").value
                val destino = it.child("destino").value
                val preco = it.child("preco").value
                val tempoMedio = it.child("tempoMedio").value

                txtRegiaoViagemVG.text = regiao.toString()
                txtOrigemViagemVG.text = origem.toString()
                txtDestinoViagemVG.text = destino.toString()
                txtPrecoVG.text = preco.toString()
                txtTempoViagemVG.text = tempoMedio.toString()

            }
        } else {
            Toast.makeText(this, "Viagem não encontrada!", Toast.LENGTH_SHORT).show()
        }
    }
}