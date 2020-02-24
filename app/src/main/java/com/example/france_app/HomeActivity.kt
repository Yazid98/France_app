package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_.*

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)

        logout_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                this@HomeActivity,
                "Deconnexion réussie !",
                Toast.LENGTH_SHORT
            ).show()

            newActivity()
        }
    }

    private fun newActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
