package com.preko.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.preko.speedcarv2.R
import com.preko.speedcarv2.databinding.ActivityRegInfoPessoalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegInfoPessoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegInfoPessoalBinding
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_info_pessoal)
        binding = ActivityRegInfoPessoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Persistência dos dados
        binding.btnSalvarRGIP.setOnClickListener {
            if (validarDados() == true) {
                salvarDados()
                navigateToHome()
            } else {
                Toast.makeText(this, "Preencha todos os campos adequadamente!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun salvarDados() {
        var nomeCompleto = binding.txtNomeCompletoRGIP.text.toString()
        var dtNascimento = binding.txtDtNascimentoRGIP.text.toString()
        var cpf = binding.txtCpfRGIP.text.toString()
        var telefone = binding.txtTelefoneRGIP.text.toString()
        var endereco = binding.txtEnderecoRGIP.text.toString()
        var modeloVeiculo = binding.txtModeloVeiculoRGIP.text.toString()
        var placaVeiculo = binding.txtPlacaVeiculoRGIP.text.toString()
        var corVeiculo = binding.txtCpfRGIP.text.toString()

        val user = com.preko.speedcarv2.model.UserModel(
            nomeCompleto, dtNascimento, cpf,
            telefone, endereco, modeloVeiculo,
            corVeiculo, placaVeiculo
        )

        if (uid != null) {
            FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .setValue(user)
                .addOnSuccessListener {
                    Toast.makeText(
                        this, "Usuário cadastrado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener {
                    Toast.makeText(
                        this, "Erro ao salvar as informações!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }


    }

    private fun validarDados(): Boolean {
        val txtNomeCompleto = binding.txtNomeCompletoRGIP.text.toString()
        val txtDtNascimento = binding.txtDtNascimentoRGIP.text.toString()
        val txtCpf = binding.txtCpfRGIP.text.toString()
        val txtTelefone = binding.txtTelefoneRGIP.text.toString()
        val txtEndereco = binding.txtEnderecoRGIP.text.toString()
        val txtModeloVeiculo = binding.txtModeloVeiculoRGIP.text.toString()
        val txtCorVeiculo = binding.txtCorVeiculoRGIP.text.toString()
        val txtPlacaVeiculo = binding.txtPlacaVeiculoRGIP.text.toString()


        if (txtNomeCompleto.length < 5) {
            return false
        } else if (txtDtNascimento.length < 2) {
            return false
        } else if (txtCpf.length < 8) {
            return false
        } else if (txtTelefone.length < 8) {
            return false
        } else if (txtEndereco.length < 5) {
            return false
        } else if (txtModeloVeiculo.length < 2) {
            return false
        } else if (txtCorVeiculo.length < 2) {
            return false
        } else if(txtPlacaVeiculo.length < 3) {
            return false
        } else {
            return true
        }
    }

    fun navigateToHome() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        startActivity(homeActivity)
    }

}