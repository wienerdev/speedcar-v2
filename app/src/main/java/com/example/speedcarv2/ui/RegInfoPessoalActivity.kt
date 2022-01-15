package com.example.speedcarv2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.speedcarv2.R
import com.example.speedcarv2.databinding.ActivityRegInfoPessoalBinding
import com.example.speedcarv2.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_reg_info_pessoal.*

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
                Toast.makeText(this, "Preencha todos os campos adequadamente!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarDados() {
        val nomeCompleto = binding.edtNomeCompletoRGIP.toString()
        val dtNascimento = binding.edtDtNascimentoRGIP.toString()
        val cpf = binding.edtCpfRGIP.toString()
        val telefone = binding.edtTelefoneRGIP.toString()
        val endereco = binding.edtEnderecoRGIP.toString()
        val modeloVeiculo = binding.edtModeloVeiculoRGIP.toString()
        val placaVeiculo = binding.edtPlacaVeiculoRGIP.toString()
        val corVeiculo = binding.edtCpfRGIP.toString()

        val user = UserModel(
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


        if(txtNomeCompleto.length < 5) {
            return false
        } else if(txtDtNascimento.length < 2) {
            return false
        } else if(txtDtNascimento.length < 5) {
            return false
        }else if(txtCpf.length < 10) {
            return false
        }else if(txtTelefone.length < 9) {
            return false
        }else if(txtEndereco.length < 5) {
            return false
        }else if(txtModeloVeiculo.length < 2) {
            return false
        }else if(txtCorVeiculo.length < 2) {
            return false
        }else return txtPlacaVeiculo.length >= 3
    }

    fun navigateToHome() {
        val homeActivity = Intent(this, HomeActivity::class.java)
        startActivity(homeActivity)
    }

}