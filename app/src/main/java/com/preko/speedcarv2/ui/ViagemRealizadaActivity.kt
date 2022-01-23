package com.preko.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityViagemRealizadaBinding
import com.google.firebase.auth.FirebaseAuth

class ViagemRealizadaActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityViagemRealizadaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViagemRealizadaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltarVGRL.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
            startActivity(homeActivity)
        }

        binding.btnIniciarViagemVGRL.setOnClickListener {
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
        startActivity(Intent(this@ViagemRealizadaActivity, LoginActivity::class.java))
        finish()
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