package com.example.speedcarv2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.speedcarv2.databinding.ActivityCriarViagemBinding
import com.example.speedcarv2.model.ViagemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError




class CriarViagemActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityCriarViagemBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarViagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelarCRVG.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
            startActivity(homeActivity)
        }

        binding.btnSalvarViagemCRVG.setOnClickListener {
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
                R.id.contaUsuario -> navigateToAccount()

                R.id.viagens -> navigateToViagens()

                R.id.pagamentos -> Toast.makeText(applicationContext, "Clicou em Pagamentos",
                    Toast.LENGTH_SHORT).show()

                R.id.logout -> logout()
            }
            true
        }

        // Persistência dos dados
        binding.btnSalvarViagemCRVG.setOnClickListener {

            val regiao = binding.edtRegiaoCRVG.text.toString()
            val origem = binding.edtOrigemCRVG.text.toString()
            val destino = binding.edtDestinoCRVG.text.toString()
            val preco = binding.edtPreOCRVG.text.toString()
            val tempoMedio = binding.edtTempoMedioCRVG.text.toString()

            val viagem = ViagemModel(regiao, origem, destino,
                preco, tempoMedio)

            val firebaseDatabase = FirebaseDatabase.getInstance()
            val databaseReference = firebaseDatabase.reference

            databaseReference.child("Trips").push().setValue(viagem)

            /* val key = database.getReference("Trips").push().key

            if (viagemKey != null) {
                FirebaseDatabase.getInstance()
                    .getReference("Trips")
                    .child(viagemKey)
                    .setValue(viagem)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Erro ao salvar as informações!", Toast.LENGTH_SHORT).show()
                    }
            } */
            navigateToViagens()
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
        startActivity(Intent(this@CriarViagemActivity, LoginActivity::class.java));
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

