package com.example.france_app.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.example.france_app.goToActivity
import kotlinx.android.synthetic.main.activity_beginning.*


class Beginning : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginning)

        button_commencer.setOnClickListener {
            goToActivity<FirstActivity>()
        }
    }
}