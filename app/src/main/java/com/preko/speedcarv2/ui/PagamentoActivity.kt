package com.preko.speedcarv2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.PurchaseInfo
import com.google.firebase.auth.FirebaseAuth
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityPagamentoBinding
import kotlinx.android.synthetic.main.activity_pagamento.*

class PagamentoActivity : AppCompatActivity(), BillingProcessor.IBillingHandler {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityPagamentoBinding

    private lateinit var bp: BillingProcessor
    private lateinit var purchaseInfo: PurchaseInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTextoPagamentoPG5.setOnClickListener {
            val googlePlay = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/intl/ALL_br/about/giftcards/#where-to-buy"))
            startActivity(googlePlay)
        }

        bp = BillingProcessor(this,
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmYoQjaGSzUXsAFjQn4mp3B8e+woMWqZGi5p92pLBy94v5ACFf78ofLF45e0/w+Upx3JmAHaShcBbkLelKnabQwTxjKHhnzMJQH9iSfVZ3WPwN03jCXnwLblCL7Cc0R/VaQngfaakfjNomjPolddXGIdwCwmxCNXsbTGQMpsYOgdLvdmvRy1UUZ75t+vNDZvWJKhjkDIzB57bk9s4SkcHPnMeS37dnMubs5Ii/n0NRIPAEWzF4dyh40ZJwX+crhELefBh4vpb8Yc1R355jha7toIf8WSUo3TLrjQkGlh8XQadFr1jMGzDciRm7CTg7HNXtZ/eLxXMdn+IxCSUMaVmDQIDAQAB",
            this)
        bp.initialize()

        binding.btnComprarPG.setOnClickListener {

            bp.subscribe(this, "plano_mensal_vem_comigo")
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

    override fun onProductPurchased(productId: String, details: PurchaseInfo?) {
        Toast.makeText(this, "Compra efetuada com sucesso!", Toast.LENGTH_SHORT).show()
    }

    override fun onPurchaseHistoryRestored() {
//        TODO("Not yet implemented")
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
//        TODO("Not yet implemented")
    }

    override fun onBillingInitialized() {
//        TODO("Not yet implemented")

        bp.loadOwnedPurchasesFromGoogleAsync(object : BillingProcessor.IPurchasesResponseListener{
            override fun onPurchasesSuccess() {
                TODO("Not yet implemented")
            }

            override fun onPurchasesError() {
                TODO("Not yet implemented")
            }

        })

        if (bp.getSubscriptionPurchaseInfo("plano_mensal_vem_comigo") != null) {

            purchaseInfo = bp.getSubscriptionPurchaseInfo("plano_mensal_vem_comigo")!!

            if (purchaseInfo != null) {
                if (purchaseInfo.purchaseData.autoRenewing) {
                    Toast.makeText(this, "Você já é um assinante!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Você não é um assinante!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Assinatura vencida!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        if (bp != null) {
            bp.release()
        }
        super.onDestroy()
    }


}