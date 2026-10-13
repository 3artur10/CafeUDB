package com.favicode.catlogoeinventariodetienda.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.favicode.catlogoeinventariodetienda.R // <--- ¡Importación de R agregada!
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        buttonRegister = findViewById(R.id.btnRegister)
        buttonRegister.setOnClickListener {
            // Sincronizado con etRegEmail y etRegPassword de tu layout de registro
            val email = findViewById<EditText>(R.id.etRegEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etRegPassword).text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                this.register(email, password)
            } else {
                Toast.makeText(this, "Correo válido y clave de mínimo 6 caracteres", Toast.LENGTH_SHORT).show()
            }
        }

        // Si usas el texto de volver al login en tu layout con ID tvGoToLogin:
        textViewLogin = findViewById(R.id.tvGoToLogin)
        textViewLogin.setOnClickListener {
            finish()
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Error al registrar: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}