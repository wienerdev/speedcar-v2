package com.preko.speedcarv2.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.preko.speedcarv2.databinding.ActivityViagensBinding
import com.preko.speedcarv2.utils.ViagemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.preko.speedcarv2.R


class ViagensActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityViagensBinding

    var viagemList: ArrayList<com.preko.speedcarv2.model.ViagemModel> = arrayListOf<com.preko.speedcarv2.model.ViagemModel>()

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val rootRef = FirebaseDatabase.getInstance().reference
    val viagemRef = uid?.let { rootRef.child("Trips").child(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViagensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lvViagens.layoutManager = LinearLayoutManager(this)
        binding.lvViagens.setHasFixedSize(true)
        readData()

        binding.btnVoltarVGS.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
            startActivity(homeActivity)
        }

        binding.btnCriarViagemVGS.setOnClickListener {
            val criarViagemActivity = Intent(this, CriarViagemActivity::class.java)
            startActivity(criarViagemActivity)
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
        startActivity(Intent(this, LoginActivity::class.java));
        finish();
    }

    fun navigateToAccount() {
        val accountActivity = Intent(this, com.preko.speedcarv2.ui.AccountActivity::class.java)
        startActivity(accountActivity)
    }

    fun navigateToViagens() {
        val viagensActivity = Intent(this, ViagensActivity::class.java);
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

    fun readData() {
        val valueEventListener = object : ValueEventListener, ViagemAdapter(viagemList) {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (viagemSnapshot in dataSnapshot.children) {

                        val viagem = viagemSnapshot.getValue(com.preko.speedcarv2.model.ViagemModel::class.java)

                        viagemList.add(viagem!!)

                        binding.lvViagens.adapter = ViagemAdapter(viagemList)

                    }
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