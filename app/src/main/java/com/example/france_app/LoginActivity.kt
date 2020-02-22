package com.example.france_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

@Suppress("RedundantSamConstructor")
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var loginBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEt = findViewById(R.id.login_email)
        passwordEt = findViewById(R.id.login_password)
        loginBtn = findViewById(R.id.login_button)

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener(){

            var email : String = emailEt.text.toString()
            var password : String = passwordEt.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(this, "Veuillez renseigner tout les champs", Toast.LENGTH_LONG).show()
            }
            else
            {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener (this, OnCompleteListener { task ->
                    if ( task.isSuccessful){
                        Toast.makeText(this, "Connexion réussie!", Toast.LENGTH_LONG).show()
                        //and I put the next activity
                        val newActivity2 = Intent (this, Home_Activity::class.java)
                        startActivity(newActivity2)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this,"Connexion échouée", Toast.LENGTH_LONG ).show()
                    }
                })
            }
        }
    }
}
