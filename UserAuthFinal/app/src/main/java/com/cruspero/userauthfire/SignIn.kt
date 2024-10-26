package com.tapon.userauthfire

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.tapon.userauthfire.databinding.ActivitySignInBinding

class SignIn : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.loginBtn.setOnClickListener {
            signIn()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun signIn() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirm = binding.confirm.text.toString()

        if(email.isEmpty() && password.isEmpty() && confirm.isEmpty()){
            Toast.makeText(this, "Please fill in empty fields", Toast.LENGTH_SHORT).show()
        }
        if(password != confirm) {
        Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
        if(it.isSuccessful) {
         val intent = Intent(this, Login::class.java)
         startActivity(intent)
        } else {
        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
        }
        }
    }
}