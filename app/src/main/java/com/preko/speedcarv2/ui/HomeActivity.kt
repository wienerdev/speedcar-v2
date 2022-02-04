package com.preko.speedcarv2.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityHomeBinding
import com.preko.speedcarv2.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.preko.speedcarv2.utils.HomeAdapter
import com.preko.speedcarv2.utils.ViagemAdapter

import androidx.annotation.NonNull
import com.android.billingclient.api.*
import com.preko.speedcarv2.utils.Prefs


class HomeActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    var viagemList: ArrayList<com.preko.speedcarv2.model.ViagemModel> = arrayListOf<com.preko.speedcarv2.model.ViagemModel>()

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityHomeBinding

    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val rootRef = FirebaseDatabase.getInstance().reference
    val viagemRef = uid?.let { rootRef.child("Trips").child(it) }

    private lateinit var billingClient: BillingClient
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSubscription()

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

    fun navigateViagemRealizada() {
        val viagemRealizadaActivity = Intent(this, ViagemRealizadaActivity::class.java)
        startActivity(viagemRealizadaActivity)
    }

    fun checkSubscription() {
        billingClient = BillingClient.newBuilder(this).enablePendingPurchases()
            .setListener { billingResult: BillingResult?, list: List<Purchase?>? -> }
            .build()

        val finalBillingClient: BillingClient = billingClient

        billingClient.startConnection(object : BillingClientStateListener {

            override fun onBillingServiceDisconnected() {

            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    finalBillingClient.queryPurchasesAsync(
                        BillingClient.SkuType.SUBS
                    ) { billingResult1: BillingResult, list: List<Purchase>? ->
                        if (billingResult1.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                            var i = 0
                            for (purchase in list) {
                                if (purchase.skus[i] == "assinatura_mensal_likeacar") {
                                    Log.d("SubTest", purchase.toString() + "")
                                    prefs.setPremium(1)
                                } else {
                                    prefs.setPremium(0)
                                    navigateToPagamento()
                                }
                                i++
                            }
                        }
                    }
                }
            }
        })
    }


}