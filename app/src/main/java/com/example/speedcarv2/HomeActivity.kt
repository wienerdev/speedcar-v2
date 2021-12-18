package com.example.speedcarv2

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)
        val btnDetalhes = findViewById<Button>(R.id.btnDetalhes)
        val btnDetalhes2 = findViewById<Button>(R.id.btnDetalhes2)

        btnDetalhes.setOnClickListener {
            navigateViagemRealizada()
        }

        btnDetalhes2.setOnClickListener {
            navigateViagemRealizada()
        }

        // Menu Hamburguer
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.contaUsuario -> Toast.makeText(applicationContext, "Clicou em Conta",
                Toast.LENGTH_SHORT).show()

                R.id.viagens -> Toast.makeText(applicationContext, "Clicou em Viagens",
                    Toast.LENGTH_SHORT).show()

                R.id.viagensRealizadas -> Toast.makeText(applicationContext, "Clicou em Viagens Realizadas",
                    Toast.LENGTH_SHORT).show()

                R.id.pagamentos -> Toast.makeText(applicationContext, "Clicou em Pagamentos",
                    Toast.LENGTH_SHORT).show()

                R.id.logout -> logout()
            }
            true
        }

    }

    private fun navigateViagemRealizada() {
        val viagemRealizadaActivity = Intent(this, ViagemRealizadaActivity::class.java);
        startActivity(viagemRealizadaActivity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java));
        finish();
    }
}