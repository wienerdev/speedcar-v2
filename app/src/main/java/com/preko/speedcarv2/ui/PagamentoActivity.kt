package com.preko.speedcarv2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityPagamentoBinding
import kotlinx.android.synthetic.main.activity_pagamento.*
import com.android.billingclient.api.BillingClient
import com.preko.speedcarv2.viewModel.QonversionViewModel
import com.qonversion.android.sdk.Qonversion
import com.qonversion.android.sdk.QonversionError
import com.qonversion.android.sdk.QonversionOfferingsCallback
import com.qonversion.android.sdk.QonversionPermissionsCallback
import com.qonversion.android.sdk.dto.QPermission
import com.qonversion.android.sdk.dto.offerings.QOfferings


class PagamentoActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityPagamentoBinding

    val viewModelQonv: QonversionViewModel = QonversionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTextoPagamentoPG5.setOnClickListener {
            val googlePlay = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/intl/ALL_br/about/giftcards/#where-to-buy"))
            startActivity(googlePlay)
        }

        btnComprarPG.setOnClickListener {
            Qonversion.purchase(
                this,
                "plano_mensal_vem_comigo",
                object : QonversionPermissionsCallback {
                    override fun onError(error: QonversionError) {
                        Toast
                            .makeText(
                                this@PagamentoActivity,
                                "A compra falhou: ${error.description}, ${error.additionalMessage}",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }

                    override fun onSuccess(permissions: Map<String, QPermission>) {
                        Toast.makeText(
                            this@PagamentoActivity, "Compra aprovada!", Toast.LENGTH_SHORT
                        ).show()

                        viewModelQonv.updatePermissions()
                        navigateToHome()
                    }
                })
        }
    }

    fun navigateToHome() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        startActivity(homeActivity)
    }

}
