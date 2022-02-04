package com.preko.speedcarv2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityPagamentoBinding
import kotlinx.android.synthetic.main.activity_pagamento.*
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.SkuDetails
import android.content.ContentValues.TAG
import android.util.Log
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.preko.speedcarv2.utils.Prefs


class PagamentoActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityPagamentoBinding

    private lateinit var billingClient: BillingClient
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar um BillingClient
        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener { billingResult, list ->
                if (billingResult.getResponseCode() === BillingClient.BillingResponseCode.OK && list != null) {
                    for (purchase in list) {
                        verifySubPurchase(purchase)
                    }
                }
            }.build()

        //start the connection after initializing the billing client
        establishConnection()

        binding.tvTextoPagamentoPG5.setOnClickListener {
            val googlePlay = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/intl/ALL_br/about/giftcards/#where-to-buy"))
            startActivity(googlePlay)
        }

        binding.btnVoltarPG.setOnClickListener {
            val homeActivity = Intent(this, HomeActivity::class.java);
            startActivity(homeActivity)
        }

        // Menu Hamburguer
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeApp -> navigateToHome()

                R.id.contaUsuario -> navigateToAccount()

                R.id.viagens -> navigateToViagens()

                R.id.pagamentos -> navigateToPagamento()

                R.id.logout -> logout()
            }
            true
        }
    }

    fun establishConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    showProducts()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                establishConnection()
            }
        })
    }

    fun showProducts() {
        // The BillingClient is ready. You can query purchases here.
        val skuList= ArrayList<String>()
        skuList.add("assinatura_mensal_likeacar")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
        billingClient.querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                // Process the result.
                for (skuDetails in skuDetailsList) {
                    if (skuDetails.sku == "assinatura_mensal_likeacar") {
                        binding.btnComprarPG.setOnClickListener {
                            launchPurchaseFlow(skuDetails)
                        }
                    }
                }
            }
        }
    }

    fun launchPurchaseFlow(skuDetails: SkuDetails?) {
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails!!)
            .build()
        billingClient.launchBillingFlow(this@PagamentoActivity, billingFlowParams)
    }

    fun verifySubPurchase(purchases: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams
            .newBuilder()
            .setPurchaseToken(purchases.purchaseToken)
            .build()
        billingClient.acknowledgePurchase(
            acknowledgePurchaseParams
        ) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                //Toast.makeText(SubscriptionActivity.this, "Item Consumed", Toast.LENGTH_SHORT).show();
                // Handle the success of the consume operation.
                //user prefs to set premium
                Toast.makeText(this@PagamentoActivity, "Assinatura realizada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
                //updateUser();

                //Setting premium to 1
                // 1 - premium
                //0 - no premium
                prefs.setPremium(1)
            }
        }
        Log.d(TAG, "Purchase Token: " + purchases.purchaseToken)
        Log.d(TAG, "Purchase Time: " + purchases.purchaseTime)
        Log.d(TAG, "Purchase OrderID: " + purchases.orderId)
    }

    override fun onResume() {
        super.onResume()
        billingClient.queryPurchasesAsync(
            BillingClient.SkuType.SUBS
        ) { billingResult, list ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in list) {
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                        verifySubPurchase(purchase)
                    }
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this@PagamentoActivity, LoginActivity::class.java))
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