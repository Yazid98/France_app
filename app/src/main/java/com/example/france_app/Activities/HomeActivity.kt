package com.example.france_app.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)

        //Deconnexion_Button

        logout_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                this@HomeActivity,
                "Deconnexion !",
                Toast.LENGTH_SHORT
            ).show()

            goToActivity<LoginActivity>()
        }

        //End-Deconnexion_Button

    }

}