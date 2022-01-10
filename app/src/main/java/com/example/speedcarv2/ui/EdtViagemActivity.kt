package com.example.speedcarv2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.speedcarv2.R
import com.example.speedcarv2.databinding.ActivityEdtViagemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_criar_viagem.*
import kotlinx.android.synthetic.main.activity_edit_account.*
import kotlinx.android.synthetic.main.activity_edt_viagem.*

class EdtViagemActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityEdtViagemBinding
    private lateinit var database: DatabaseReference
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdtViagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (uid != null) {
            readViagemData(uid)
        }

        binding.btnCancelarEDVG.setOnClickListener {
            val viagensActivity = Intent(this, ViagensActivity::class.java);
            startActivity(viagensActivity)
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
        startActivity(Intent(this@EdtViagemActivity, LoginActivity::class.java));
        finish();
    }

    fun navigateToAccount() {
        val accountActivity = Intent(this, AccountActivity::class.java);
        startActivity(accountActivity)
    }

    fun navigateToHome() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        startActivity(homeActivity)
    }

    fun navigateToViagens() {
        val viagensActivity = Intent(this, ViagensActivity::class.java);
        startActivity(viagensActivity)
    }

    fun readViagemData(uid: String) {
        database = FirebaseDatabase.getInstance().getReference("Trips")
        val viagemId = database.child(uid).push().key

        if (uid != null && viagemId != null) {
            database.child(viagemId).get().addOnSuccessListener {

                val regiao = it.child("regiao").value
                val origem = it.child("origem").value
                val destino = it.child("destino").value
                val preco = it.child("preco").value
                val tempoMedio = it.child("tempoMedio").value
                val nomePassageiro = it.child("nomePassageiro").value
                val cpfPassageiro = it.child("cpfPassageiro").value
                val telefonePassageiro = it.child("telefonePassageiro").value

                edtRegiaoEDVG.setText(regiao.toString())
                edtOrigemEDVG.setText(origem.toString())
                edtDestinoEDVG.setText(destino.toString())
                edtPreçoEDVG.setText(preco.toString())
                edtTempoMedioEDVG.setText(tempoMedio.toString())
                edtNomePassageiroEDVG.setText(nomePassageiro.toString())
                edtCpfPassageiroEDVG.setText(cpfPassageiro.toString())
                edtTelefonePassageiroEDVG.setText(telefonePassageiro.toString())

            }
        } else {
            Toast.makeText(this, "Viagem não encontrada!", Toast.LENGTH_SHORT).show()
        }
    }
}