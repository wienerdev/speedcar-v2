package com.preko.speedcarv2.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.preko.speedcarv2.R
import com.preko.speedcarv2.ui.EdtViagemActivity
import com.preko.speedcarv2.ui.ViagemRealizadaActivity
import kotlinx.android.synthetic.main.viagem_card.view.*
import kotlinx.android.synthetic.main.viagem_card.view.txtDestinoViagemHM
import kotlinx.android.synthetic.main.viagem_card.view.txtOrigemViagemHM
import kotlinx.android.synthetic.main.viagem_card.view.txtPrecoHM
import kotlinx.android.synthetic.main.viagem_card.view.txtRegiaoViagemHM
import kotlinx.android.synthetic.main.viagem_card.view.txtTempoViagemHM

open class ViagemAdapter(private val viagemList: ArrayList<com.preko.speedcarv2.model.ViagemModel>)
    : RecyclerView.Adapter<ViagemAdapter.ViagemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViagemViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viagem_card,
        parent, false)

        val holder = ViagemViewHolder(itemView)

        return holder
    }

    override fun getItemCount(): Int {
        return viagemList.size
    }

    class ViagemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val regiao : TextView = itemView.txtRegiaoViagemHM
        val origem : TextView = itemView.txtOrigemViagemHM
        val destino : TextView = itemView.txtDestinoViagemHM
        val tempoMedio : TextView = itemView.txtTempoViagemHM
        val preco : TextView = itemView.txtPrecoHM
        val btnEditarViagem : Button = itemView.btnEditarViagemVG
        val context = itemView.context

    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViagemViewHolder, position: Int) {

        val currentViagem: com.preko.speedcarv2.model.ViagemModel = viagemList.get(position)

        holder.regiao.text = currentViagem.regiao
        holder.origem.text = currentViagem.origem
        holder.destino.text = currentViagem.destino
        holder.tempoMedio.text = currentViagem.tempoMedio
        holder.preco.text = currentViagem.preco

        holder.btnEditarViagem.setOnClickListener {
            val intent = Intent(holder.context, EdtViagemActivity::class.java)
            holder.context.startActivity(intent)
        }
    }


}