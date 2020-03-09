package com.example.france_app

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast

//Raccourci pour un intent
inline fun <reified T : Activity> Activity.goToActivity(
    noinline init: Intent.()
    -> Unit = {}
) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

//Raccourci pour un Toast
fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()


//Raccourci pour les différentes actions

fun EditText.validate(validation: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            validation(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}