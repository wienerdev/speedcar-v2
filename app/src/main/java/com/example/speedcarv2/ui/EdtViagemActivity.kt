package com.example.speedcarv2.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.speedcarv2.R
import com.example.speedcarv2.databinding.ActivityEdtViagemBinding
import com.example.speedcarv2.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_criar_viagem.*
import kotlinx.android.synthetic.main.activity_edit_account.*
import kotlinx.android.synthetic.main.activity_edt_viagem.*
import kotlinx.android.synthetic.main.activity_viagens.*

class EdtViagemActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityEdtViagemBinding
    private lateinit var database: DatabaseReference
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdtViagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (uid != null) {
            readData()
        }

        binding.btnCancelarEDVG.setOnClickListener {
            val viagensActivity = Intent(this, ViagensActivity::class.java);
            startActivity(viagensActivity)
        }

        binding.btnIniciarViagemEDVG.setOnClickListener {
            viewModel.openNewTabWindow(
                "https://www.google.com.br/maps/dir///@-15.8334976,-47.9133696,15z",
                this@EdtViagemActivity) // Kotlin version of getContext()
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

                R.id.pagamentos -> viewModel.openNewTabWindow("https://pagseguro.uol.com.br/#rmcl", this@EdtViagemActivity)


                R.id.logout -> logout()
            }
            true
        }
    }

    override fun onRestart() {
        super.onRestart()
        navigateToHome()
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

    fun readData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val rootRef = FirebaseDatabase.getInstance().reference
        val viagemRef = uid?.let { rootRef.child("Trips").child(it) }

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val regiao = ds.child("regiao").getValue(String::class.java)
                    val origem = ds.child("origem").getValue(String::class.java)
                    val destino = ds.child("destino").getValue(String::class.java)
                    val preco = ds.child("preco").getValue(String::class.java)
                    val tempoMedio = ds.child("tempoMedio").getValue(String::class.java)
                    val nomePassageiro = ds.child("nomePassageiro").getValue(String::class.java)
                    val cpfPassageiro = ds.child("cpfPassageiro").getValue(String::class.java)
                    val telefonePassageiro = ds.child("telefonePassageiro").getValue(String::class.java)

                    edtRegiaoEDVG.setText(regiao.toString())
                    edtOrigemEDVG.setText(origem.toString())
                    edtDestinoEDVG.setText(destino.toString())
                    edtPrecoEDVG.setText(preco.toString())
                    edtTempoMedioEDVG.setText(tempoMedio.toString())
                    edtNomePassageiroEDVG.setText(nomePassageiro.toString())
                    edtCpfPassageiroEDVG.setText(cpfPassageiro.toString())
                    edtTelefonePassageiroEDVG.setText(telefonePassageiro.toString())
                }
            }

            @SuppressLint("LongLogTag")
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Desculpe, erro no sistema", databaseError.getMessage())
            }
        }
        if (viagemRef != null) {
            viagemRef.addListenerForSingleValueEvent(valueEventListener)
        }
    }
}