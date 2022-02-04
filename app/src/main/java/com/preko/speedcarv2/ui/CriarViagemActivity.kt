package com.preko.speedcarv2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.preko.speedcarv2.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityCriarViagemBinding


class CriarViagemActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityCriarViagemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarViagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelarCRVG.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
            startActivity(homeActivity)
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
        binding.btnIniciarViagemCRVG.setOnClickListener {

            val regiao = binding.edtRegiaoCRVG.text.toString()
            val origem = binding.edtOrigemCRVG.text.toString()
            val destino = binding.edtDestinoCRVG.text.toString()
            val preco = binding.edtPrecoCRVG.text.toString()
            val tempoMedio = binding.edtTempoMedioCRVG.text.toString()
            val nomePassageiro = binding.edtNomePassageiroCRVG.text.toString()
            val cpfPassageiro = binding.edtCpfPassageiroCRVG.text.toString()
            val telefonePassageiro = binding.edtTelefonePassageiroCRVG.text.toString()

            val viagem = com.preko.speedcarv2.model.ViagemModel(
                regiao, origem, destino,
                preco, tempoMedio, nomePassageiro, cpfPassageiro, telefonePassageiro
            )

            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.reference
            val uid = FirebaseAuth.getInstance().currentUser?.uid

            if (uid != null) {
                databaseReference.child("Trips")
                    .child(uid)
                    .push()
                    .setValue(viagem)
                    .addOnSuccessListener {
                    Toast.makeText(this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Erro ao salvar as informações!", Toast.LENGTH_SHORT).show()
                }
            }

            val mapIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$destino"))
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
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
        startActivity(Intent(this@CriarViagemActivity, LoginActivity::class.java));
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

    fun navigateToPagamento() {
        val pagamentoActivity = Intent(this, PagamentoActivity::class.java)
        startActivity(pagamentoActivity)
    }

    fun navigateToHome() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        startActivity(homeActivity)
    }
}

