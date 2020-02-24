package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_.*

class Home_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)

        logout_button.setOnClickListener{
            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Vous êtes deconnecté", Toast.LENGTH_LONG).show()
            val intent = Intent( this, debut_app::class.java)
            startActivity(intent)
            finish()
        }
    }
}
