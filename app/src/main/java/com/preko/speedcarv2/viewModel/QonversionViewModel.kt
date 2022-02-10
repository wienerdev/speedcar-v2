package com.preko.speedcarv2.viewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.preko.speedcarv2.ui.EdtViagemActivity
import com.preko.speedcarv2.ui.HomeActivity
import com.preko.speedcarv2.ui.PagamentoActivity
import com.qonversion.android.sdk.Qonversion
import com.qonversion.android.sdk.QonversionError
import com.qonversion.android.sdk.QonversionOfferingsCallback
import com.qonversion.android.sdk.QonversionPermissionsCallback
import com.qonversion.android.sdk.dto.QPermission
import com.qonversion.android.sdk.dto.offerings.QOffering
import com.qonversion.android.sdk.dto.offerings.QOfferings
import com.qonversion.android.sdk.dto.products.QProduct
import kotlin.properties.Delegates

class QonversionViewModel: ViewModel() {

    var hasPremiumPermission by mutableStateOf(false)

    init {
        updatePermissions()
    }

    fun updatePermissions() {
        Qonversion.checkPermissions(object : QonversionPermissionsCallback {
            override fun onError(error: QonversionError) {
                Log.d(TAG, "onError: ${error.description}")
            }

            override fun onSuccess(permissions: Map<String, QPermission>) {
                hasPremiumPermission = permissions["plano_mensal_vem_comigo"]?.isActive() == true
                Log.d("teste", "permissions: ${permissions["plano_mensal_vem_comigo"]}")
            }
        })
    }
}