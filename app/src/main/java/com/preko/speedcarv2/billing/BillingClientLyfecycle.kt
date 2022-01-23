package com.preko.speedcarv2.billing

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import com.android.billingclient.api.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BillingClientLyfecycle private constructor(
    private val app: Application
) : LifecycleObserver, PurchasesUpdatedListener, BillingClientStateListener,
    SkuDetailsResponseListener, PurchasesResponseListener {

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }

    private var billingClient = BillingClient.newBuilder(app.applicationContext)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

    private fun conexaoGooglePlay() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    suspend fun querySkuDetails() {
        val skuList = ArrayList<String>()
        skuList.add("premium_upgrade")
        skuList.add("gas")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)

        // leverage querySkuDetails Kotlin extension function
        val skuDetailsResult = withContext(Dispatchers.IO) {
            billingClient.querySkuDetails(params.build())
        }

        // Process the result.
    }

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
        TODO("Not yet implemented")
    }

    override fun onBillingServiceDisconnected() {
        TODO("Not yet implemented")
    }

    override fun onBillingSetupFinished(p0: BillingResult) {
        TODO("Not yet implemented")
    }

    override fun onSkuDetailsResponse(p0: BillingResult, p1: MutableList<SkuDetails>?) {
        TODO("Not yet implemented")
    }

    override fun onQueryPurchasesResponse(p0: BillingResult, p1: MutableList<Purchase>) {
        TODO("Not yet implemented")
    }

}