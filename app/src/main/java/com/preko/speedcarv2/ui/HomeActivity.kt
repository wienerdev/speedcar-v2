package com.preko.speedcarv2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityHomeBinding
import com.preko.speedcarv2.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContaHM.setOnClickListener {
            navigateToAccount()
        }

        binding.btnViagensHM.setOnClickListener {
            navigateToViagens()
        }

        binding.btnPagamentoHM.setOnClickListener {
            navigateToPagamento()
        }

        binding.btnCriarViagemHM.setOnClickListener {
            val criarViagemActivity = Intent(this, CriarViagemActivity::class.java)
            startActivity(criarViagemActivity)
        }

        binding.btnDetalhesHM.setOnClickListener {
            navigateViagemRealizada()
        }

        binding.btnDetalhesHM2.setOnClickListener {
            navigateViagemRealizada()
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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        finish()
    }

    fun navigateToAccount() {
        val accountActivity = Intent(this, com.preko.speedcarv2.ui.AccountActivity::class.java)
        startActivity(accountActivity)
    }

    fun navigateToViagens() {
        val viagensActivity = Intent(this, ViagensActivity::class.java)
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

    fun navigateViagemRealizada() {
        val viagemRealizadaActivity = Intent(this, ViagemRealizadaActivity::class.java)
        startActivity(viagemRealizadaActivity)
    }
}