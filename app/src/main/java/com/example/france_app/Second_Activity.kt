package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Second_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_)

        // To display the image and the background
        val mImageView: ImageView = findViewById(R.id.logo1)
        mImageView.setImageResource(R.drawable.image_logo1)

        //To begin the third activity
        val mButton = findViewById<Button>(R.id.buttonNext2)
        mButton.setOnClickListener {
            val newActivity2 = Intent(this@Second_Activity, RegistrationActivity::class.java)
            startActivity(newActivity2)
        }

    }
}
