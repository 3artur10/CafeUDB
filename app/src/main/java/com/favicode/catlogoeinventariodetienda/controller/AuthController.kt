package com.favicode.catlogoeinventariodetienda.controller

import com.google.firebase.auth.FirebaseAuth

class AuthController {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun iniciarSesion(email: String, pass: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || pass.isEmpty()) {
            onResult(false, "Por favor completa todos los campos")
            return
        }
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Error al iniciar sesión")
                }
            }
    }

    fun registrarUsuario(email: String, pass: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isEmpty() || pass.isEmpty()) {
            onResult(false, "Por favor completa todos los campos")
            return
        }
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Error al registrarse")
                }
            }
    }
}