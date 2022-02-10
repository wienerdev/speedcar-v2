package com.preko.speedcarv2.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityHomeBinding
import com.preko.speedcarv2.utils.HomeAdapter
import com.preko.speedcarv2.viewModel.QonversionViewModel
import com.qonversion.android.sdk.Qonversion
import com.qonversion.android.sdk.QonversionError
import com.qonversion.android.sdk.QonversionOfferingsCallback
import com.qonversion.android.sdk.QonversionPermissionsCallback
import com.qonversion.android.sdk.dto.QPermission
import com.qonversion.android.sdk.dto.offerings.QOfferings


class HomeActivity : AppCompatActivity() {

    var viagemList: ArrayList<com.preko.speedcarv2.model.ViagemModel> = arrayListOf<com.preko.speedcarv2.model.ViagemModel>()

    val viewModelQonv: QonversionViewModel = QonversionViewModel()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityHomeBinding

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val rootRef = FirebaseDatabase.getInstance().reference
    val viagemRef = uid?.let { rootRef.child("Trips").child(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lvViagensHM.layoutManager = LinearLayoutManager(this)
        binding.lvViagensHM.setHasFixedSize(true)
        readData()

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

    override fun onResume() {
        super.onResume()

        Qonversion.checkPermissions(object : QonversionPermissionsCallback {
            override fun onError(error: QonversionError) {
                Log.d("teste", "onError: ${error.description}")
            }

            override fun onSuccess(permissions: Map<String, QPermission>) {
                val premiumPermission = permissions["plano_mensal_vem_comigo"]

                premiumPermission?.let {

                    if (!it.isActive()) {
                        navigateToPagamento()
                    }
                }

            }
        })
    }

    fun readData() {
        val valueEventListener = object : ValueEventListener, HomeAdapter(viagemList) {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (viagemSnapshot in dataSnapshot.children) {

                        val viagem = viagemSnapshot.getValue(com.preko.speedcarv2.model.ViagemModel::class.java)

                        viagemList.add(viagem!!)

                        binding.lvViagensHM.adapter = HomeAdapter(viagemList)

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


}