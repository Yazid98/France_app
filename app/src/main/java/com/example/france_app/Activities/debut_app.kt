package com.example.france_app.Activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.google.firebase.auth.FirebaseAuth


@Suppress("ClassName")
class debut_app : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debut_app)

        mAuth = FirebaseAuth.getInstance()

        var mButton1 = findViewById<Button>(R.id.button_deja_inscrit)
        mButton1.setOnClickListener {
            goToActivity<LoginActivity>()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }

        var mButton2 = findViewById<Button>(R.id.button_nouvel_inscrit)
        mButton2.setOnClickListener {
            goToActivity<FirstActivity>()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        goToActivity<HomeActivity>()
    }

}