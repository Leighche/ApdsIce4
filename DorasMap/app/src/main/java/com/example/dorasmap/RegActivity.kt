package com.example.dorasmap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegActivity : AppCompatActivity() {

    // Declare Firebase Authentication instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Get references to input fields and button
        val usernameField: EditText = findViewById(R.id.usernamereg)
        val passwordField: EditText = findViewById(R.id.passwordreg)
        val confirmPasswordField: EditText = findViewById(R.id.confirmpasswordreg)
        val registerButton: Button = findViewById(R.id.registerbutton)
        val redirection: TextView = findViewById(R.id.redirectreg)

        redirection.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Set onClick listener for register button
        registerButton.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            // Check if all fields are filled
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Register the user with Firebase Authentication
                registerUser(username, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registration successful, show message
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    // Optionally redirect to another activity (like Login or Home)
                } else {
                    // If registration fails, display a message to the user.
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
