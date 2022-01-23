package com.preko.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
//import com.android.billingclient.api.*
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityPagamentoBinding
import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext


class PagamentoActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityPagamentoBinding

//    private val purchasesUpdatedListener =
//        PurchasesUpdatedListener { billingResult, purchases ->
//            // To be implemented in a later section.
//        }
//
//    private var billingClient = BillingClient.newBuilder(this@PagamentoActivity)
//        .setListener(purchasesUpdatedListener)
//        .enablePendingPurchases()
//        .build()
//
//    // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.
//    val purchase : PurchasesUpdatedListener = purchasesUpdatedListener
//    val client: BillingClient = billingClient
//    val acknowledgePurchaseResponseListener: AcknowledgePurchaseResponseListener =


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        billingClient.startConnection(object : BillingClientStateListener {
//            override fun onBillingSetupFinished(billingResult: BillingResult) {
//                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
//                    // The BillingClient is ready. You can query purchases here.
//                }
//            }
//            override fun onBillingServiceDisconnected() {
//                // Try to restart the connection on the next request to
//                // Google Play by calling the startConnection() method.
//            }
//        })
//
//        suspend fun querySkuDetails() {
//            val skuList = ArrayList<String>()
//            skuList.add("premium_upgrade")
//            skuList.add("gas")
//            val params = SkuDetailsParams.newBuilder()
//            params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
//
//            // leverage querySkuDetails Kotlin extension function
//            val skuDetailsResult = withContext(Dispatchers.IO) {
//                billingClient.querySkuDetails(params.build())
//            }
//
//            // Process the result.
//        }
//
//        suspend fun handlePurchase(purchase: Purchase) {
//            // Verify the purchase.
//            // Ensure entitlement was not already granted for this purchaseToken.
//            // Grant entitlement to the user.
//
//            val consumeParams =
//                ConsumeParams.newBuilder()
//                    .setPurchaseToken(purchase.toString())
//                    .build()
//            val consumeResult = withContext(Dispatchers.IO) {
//                client.consumePurchase(consumeParams)
//            }
//        }
//
//        suspend fun handlePurchase() {
//            if (purchase.purchaseState === Purchase.PurchaseState.PURCHASED) {
//                if (!purchase.isAcknowledged) {
//                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
//                        .setPurchaseToken(purchase.purchaseToken)
//                    val ackPurchaseResult = withContext(Dispatchers.IO) {
//                        client.acknowledgePurchase(acknowledgePurchaseParams.build())
//                    }
//                }
//            }
//        }

        binding.btnComprarPG.setOnClickListener {

//            // An activity reference from which the billing flow will be launched.
//            val activity : Activity = this@PagamentoActivity;
//
//            // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
//            val flowParams = BillingFlowParams.newBuilder()
//                .setSkuDetails(skuDetailsResult)
//                .build()
//            val responseCode = billingClient.launchBillingFlow(activity, flowParams).responseCode
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

//    fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
//        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
//            for (purchase in purchases) {
//                handlePurchase(purchase)
//            }
//        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
//            // Handle an error caused by a user cancelling the purchase flow.
//        } else {
//            // Handle any other error codes.
//        }
//    }


}