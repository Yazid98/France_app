package com.example.france_app.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.example.france_app.goToActivity

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
            goToActivity<LoginActivity>()
        }

    }
}
