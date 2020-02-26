@file:Suppress("DEPRECATION")

package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("RedundantSamConstructor")
class LoginActivity : AppCompatActivity() {

    private var isShowPass = false


    private lateinit var auth: FirebaseAuth
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //For show and hide the password
        ivShowPassword.setOnClickListener {
            isShowPass = !isShowPass
            showPassword(isShowPass)
        }

        showPassword(isShowPass)

        //_____________________________________________________________________________________________________________________________________

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.apply {
            val email = getString("Email", "")
            val password = getString("Password", "")
            login_email.setText(email)
            login_password.setText(password)
        }

        emailEt = findViewById(R.id.login_email)
        passwordEt = findViewById(R.id.login_password)
        loginBtn = findViewById(R.id.login_button)

        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {

            var email: String = emailEt.text.toString()
            var password: String = passwordEt.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Veuillez renseigner tout les champs", Toast.LENGTH_LONG)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Connexion réussie!", Toast.LENGTH_LONG).show()

                            //and I put the next activity
                            val newActivity2 = Intent(this, HomeActivity::class.java)
                            startActivity(newActivity2)
                            finish()
                        } else {
                            Toast.makeText(this, "Connexion échouée", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }

        rememberMeCheckBox.setOnClickListener {
            rememberMe(emailEt, passwordEt)
        }

        reset_password_button.setOnClickListener {
            val newActivity3 = Intent(this, ResetPassword::class.java)
            startActivity(newActivity3)
            finish()
        }
    }

    private fun rememberMe(email: EditText, passwordEt: EditText) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor
            .putString("Email", login_email.text.toString())
            .putString("Password", login_password.text.toString())
            .apply()

        val toast = Toast.makeText(applicationContext, "We will remember you!", Toast.LENGTH_LONG)

        toast.setGravity(Gravity.TOP, 0, 140)
        toast.show()
    }


    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            // To show the password
            login_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            ivShowPassword.setImageResource(R.drawable.ic_hide_password_24dp)
        } else {
            // To hide the password
            login_password.transformationMethod = PasswordTransformationMethod.getInstance()
            ivShowPassword.setImageResource(R.drawable.ic_show_password_24dp)
        }
        // This line of code to put the pointer at the end of the password string
        login_password.setSelection(login_password.text.toString().length)
    }

}
