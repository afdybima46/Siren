package com.example.siren.views.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.siren.MainActivity
import com.example.siren.ProviderType
import com.example.siren.databinding.ActivityLoginBinding
import com.example.siren.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        login()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navigateToHome(
                            task.result.user?.email ?: "",
                            task.result.user?.uid ?: "",
                            ProviderType.BASIC
                        )
                        finish()
                        binding.etEmail.text?.clear()
                        binding.etPassword.text?.clear()
                    } else {
                        showAlert()
                        binding.etEmail.text?.clear()
                        binding.etPassword.text?.clear()
                    }
                }.addOnFailureListener { exception ->
                    exception.localizedMessage?.let { toast(this, it) }
                }
            } else if (email.isBlank()) {
                binding.etEmail.error = "EMAIL REQUIRED"
            } else if (password.isBlank()) {
                binding.etPassword.error = "PASSWORD REQUIRED"
            }
        }
    }

    private fun navigateToHome(email: String, usersId: String, provider: ProviderType) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("UserId", usersId)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("An Authentication Error")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}