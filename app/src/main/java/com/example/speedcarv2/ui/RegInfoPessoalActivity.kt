package com.example.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.speedcarv2.R
import com.example.speedcarv2.databinding.ActivityRegInfoPessoalBinding
import com.example.speedcarv2.model.UserModel
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

            val nomeCompleto = binding.edtNomeCompletoEdtRGIP.text.toString()
            val dtNascimento = binding.edtDtNascimentoEdtRGIP.text.toString()
            val cpf = binding.edtCpfEdtRGIP.text.toString()
            val telefone = binding.edtTelefoneEdtRGIP.text.toString()
            val endereco = binding.edtEnderecoEdtRGIP.text.toString()
            val modeloVeiculo = binding.edtModeloVeiculoEdtRGIP.text.toString()
            val placaVeiculo = binding.edtPlacaVeiculoEdtRGIP.text.toString()
            val corVeiculo = binding.edtCpfEdtRGIP.text.toString()

            val user = UserModel(
                nomeCompleto, cpf,
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
            navigateToHome()
        }

    }

    fun navigateToHome() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        startActivity(homeActivity)
    }

}