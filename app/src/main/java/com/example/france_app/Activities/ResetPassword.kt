@file:Suppress("RedundantSamConstructor")

package com.example.france_app.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPassword : AppCompatActivity() {

    private val TAG = "ResetPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        btn_reset_password.setOnClickListener {
            resetPassword()
        }


        /* val auth = FirebaseAuth.getInstance()
         val emailAddress = "user@example.com"

         auth.sendPasswordResetEmail(emailAddress)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     Log.d(TAG, "Email sent.")
                 }
             } */
    }

    private fun resetPassword() {
        val email = et_email.text.toString()
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Log.d(TAG, "Email sent.")
                Toast.makeText(this, "Password Emal sent successfully !", Toast.LENGTH_LONG).show()
            } else {
                Log.d(TAG, "Fail to sent email.")
                Toast.makeText(this, "Password Emal sent successfully !", Toast.LENGTH_LONG).show()
            }
        }

        updatePassword()
        newActivity()
    }

    private fun updatePassword() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val password = "Some-Secure-Password"
        mAuth.currentUser?.updatePassword(password)
            ?.addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password changes successfully", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this, "password not changed", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun newActivity() {
        goToActivity<LoginActivity>()
        finish()
    }

}
