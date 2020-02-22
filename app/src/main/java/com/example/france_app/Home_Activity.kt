package com.example.france_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Home_Activity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var logoutBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)

        auth = FirebaseAuth.getInstance()

        logoutBtn = findViewById(R.id.logout_button)

        logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Vous êtes deconnecté", Toast.LENGTH_LONG).show()
            val intent = Intent( this, debut_app::class.java)
            startActivity(intent)
            finish()
        }
    }
}
