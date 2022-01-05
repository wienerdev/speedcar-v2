package com.example.speedcarv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.speedcarv2.databinding.ActivityViagensBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.account_activity.*
import kotlinx.android.synthetic.main.activity_viagens.*

class ViagensActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityViagensBinding

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
        val rootRef = FirebaseDatabase.getInstance().reference
        val viagemRef = rootRef.child("Trips")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val regiao = ds.child("regiao").getValue(String::class.java)
                    val origem = ds.child("origem").getValue(String::class.java)
                    val destino = ds.child("destino").getValue(String::class.java)
                    val preco = ds.child("preco").getValue(String::class.java)
                    val tempoMedio = ds.child("tempoMedio").getValue(String::class.java)

                    txtRegiaoViagemVG.text = regiao.toString()
                    txtOrigemViagemVG.text = origem.toString()
                    txtDestinoViagemVG.text = destino.toString()
                    txtPrecoVG.text = "R$" + preco.toString()
                    txtTempoViagemVG.text = tempoMedio.toString() + " minutos"
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.getMessage()) //Don't ignore errors!
            }
        }
        viagemRef.addListenerForSingleValueEvent(valueEventListener)
    }
}