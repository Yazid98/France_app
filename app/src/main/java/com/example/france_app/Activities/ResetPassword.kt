@file:Suppress("RedundantSamConstructor")

package com.example.france_app.Activities

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.example.france_app.goToActivity
import com.example.france_app.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.toast_error.*

class ResetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        et_email.setOnClickListener {
            et_email.isCursorVisible = true
        }

        btn_reset_password.setOnClickListener {
            resetPassword()
        }

        back_to_login.setOnClickListener {
            goToActivity<LoginActivity>()
        }
    }

    private fun resetPassword() {
        val email = et_email.text.toString()
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        when {
            TextUtils.isEmpty(email) -> toast("Remplissez votre email")
            else -> {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        doneToast()
                        goToActivity<LoginActivity>()
                    } else {
                        errorToast()
                    }
                }
            }
        }
    }

    //To show the custome toast
    private fun doneToast() {
        val layout: View = layoutInflater.inflate(R.layout.toast_done, linearParent)
        val toast: Toast = Toast(applicationContext)
        toast.apply {
            setGravity(Gravity.BOTTOM, 0, 50)
//            setText(" :-) Good !")
            view = layout
            duration = Toast.LENGTH_SHORT
            show()
        }
    }

    private fun errorToast() {
        val layout: View = layoutInflater.inflate(R.layout.toast_error, linearParent)
        val toast: Toast = Toast(applicationContext)
        toast.apply {
            setGravity(Gravity.BOTTOM, 0, 50)
//            setText(" :-) Good !")
            view = layout
            duration = Toast.LENGTH_SHORT
            show()
        }
    }

}
