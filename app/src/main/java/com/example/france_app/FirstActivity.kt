package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // To display the image and the background
        val mImageView: ImageView = findViewById(R.id.logo1)
        mImageView.setImageResource(R.drawable.image_logo1)

        //To begin the second activity
        val mButton = findViewById<Button>(R.id.buttonNext)
        mButton.setOnClickListener {
            val newActivity = Intent(this@FirstActivity, Second_Activity::class.java)
            startActivity(newActivity)
        }
    }
}