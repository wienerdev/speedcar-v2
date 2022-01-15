package com.example.speedcarv2.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speedcarv2.R
import com.example.speedcarv2.model.ViagemModel

class ViagemAdapter(private val viagemList: ArrayList<ViagemModel>)
    : RecyclerView.Adapter<ViagemAdapter.ViagemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViagemViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viagem_card,
        parent, false)
        return ViagemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViagemViewHolder, position: Int) {

        val currentItem = viagemList[position]

        holder.regiao.text = currentItem.regiao
        holder.origem.text = currentItem.origem
        holder.destino.text = currentItem.destino
        holder.tempoMedio.text = currentItem.tempoMedio
        holder.preco.text = currentItem.preco
    }

    override fun getItemCount(): Int {
        return viagemList.size
    }

    class ViagemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val regiao : TextView = itemView.findViewById(R.id.txtRegiaoViagemVG)
        val origem : TextView = itemView.findViewById(R.id.txtOrigemViagemVG)
        val destino : TextView = itemView.findViewById(R.id.txtDestinoViagemVG)
        val tempoMedio : TextView = itemView.findViewById(R.id.txtTempoViagemVG)
        val preco : TextView = itemView.findViewById(R.id.txtPrecoVG)


    }


}