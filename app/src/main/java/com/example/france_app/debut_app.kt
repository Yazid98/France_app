package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class debut_app : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debut_app)

        var mButton1 = findViewById<Button>(R.id.button_deja_inscrit)
        mButton1.setOnClickListener{
            val newActivity = Intent ( this@debut_app, LoginActivity::class.java)
            startActivity(newActivity)
        }

        var mButton2 = findViewById<Button>(R.id.button_nouvel_inscrit)
        mButton2.setOnClickListener{
            val newActivity2 = Intent(this@debut_app, HomeActivity::class.java)
            startActivity(newActivity2)
        }
    }
}
