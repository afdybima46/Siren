package com.example.siren.views.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.siren.databinding.ActivityRegisterBinding
import com.example.siren.utils.toast
import com.example.siren.views.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var auth: FirebaseAuth

    var databaseReference: DatabaseReference? = null
    var databases: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databases = FirebaseDatabase.getInstance()
        databaseReference = databases?.reference!!.child("Users")

        binding.btnLogin.setOnClickListener { navigateToLogin() }
        register()
    }

    private fun register() {
        binding.btnRegister.setOnClickListener {

            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                    currentUserDb?.child("Name")?.setValue(name)
                    currentUserDb?.child("Email")?.setValue(email)
                    currentUserDb?.child("Password")?.setValue(password)

                    toast(this, "Registration Success, please login")
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    toast(this, "Registration Failed, please try again!")
                }
            }.addOnFailureListener { exception ->
                exception.localizedMessage?.let { toast(this, it) }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}