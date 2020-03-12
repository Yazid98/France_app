@file:Suppress("DEPRECATION")

package com.example.france_app.Activities

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.example.france_app.goToActivity
import com.example.france_app.toast
import com.example.france_app.validate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        //To select calendar when the user wants to choose his birthday

        var date = findViewById<TextView>(R.id.tv_dateNaissance)

        val calendar = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                date.text = sdf.format(calendar.time)
            }

        date.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        //End of the Calendar selection

        //To introduce the cursor when the user click on the editText

        et_first_name.setOnClickListener {
            et_first_name.isCursorVisible = true
        }

        et_last_name.setOnClickListener {
            et_last_name.isCursorVisible = true
        }

        et_email.setOnClickListener {
            et_email.isCursorVisible = true
        }

        et_password.setOnClickListener {
            et_password.isCursorVisible = true
        }

        //End of cursor introducing

        //Create account button
        btn_register.setOnClickListener {
            createAccount()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        //Validation of Email and Password

        et_email.validate {
            et_email.error =
                if (isValideEmail(et_email.text.toString())) null else "Format de l'email non valide"
        }

        et_password.validate {
            et_password.error =
                if (isValidePassword(et_password.text.toString())) null else "Votre mot de passe doit contenir 8 caractères au minimums dont une lettre majuscule, une lettre miniscule, et un chiffre entre 0 et 9"
        }

        //End validation of Email and Password
    }

    //Creation Compte

    private fun createAccount() {

        val firstName = et_first_name.text.toString()
        val lastName = et_last_name.text.toString()
        val dateNaissance = tv_dateNaissance.text.toString()
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        when {
            TextUtils.isEmpty(firstName) -> toast("Remplissez tous les champs")

            TextUtils.isEmpty(lastName) -> toast("Remplissez tous les champs")

            TextUtils.isEmpty(dateNaissance) -> toast("Remplissez tous les champs")

            TextUtils.isEmpty(email) -> toast("Remplissez tous les champs")

            TextUtils.isEmpty(password) -> toast("Remplissez tous les champs")

            else -> {

                //For progress dialogue
                val progressDialog = ProgressDialog(this@CreateAccount)
                progressDialog.setTitle("Signing Up")
                progressDialog.setMessage("Please wait, this may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            //Verify email address
                            val mUser = mAuth.currentUser
                            mUser?.sendEmailVerification()?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    toast("Email verification sent!")
                                    goToActivity<LoginActivity>()
                                }
                            }

                            saveUserInfo(
                                firstName,
                                lastName,
                                dateNaissance,
                                email,
                                password,
                                progressDialog
                            )

                        } else {
                            val message = task.exception!!.toString()
                            toast("Error $message")
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    //End Creation Compte


    //Save information in database
    private fun saveUserInfo(
        firstName: String,
        lastName: String,
        dateNaissance: String,
        email: String,
        password: String,
        progressDialog: ProgressDialog
    ) {

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //Update user profile information
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val currentUserDb = userRef.child(userId)
        currentUserDb.child("firstName").setValue(firstName)
        currentUserDb.child("lastName").setValue(lastName)
        currentUserDb.child("birthday").setValue(dateNaissance)
        currentUserDb.child("email").setValue(email)
        currentUserDb.child("password").setValue(password)

        FirebaseAuth.getInstance().signOut()
        progressDialog.dismiss()
    }
    //End save information in database


    //Function for validation Email and Password

    //Function for specific email address
    private fun isValideEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    //Function for specific password
    private fun isValidePassword(password: String): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        return pattern.matcher(password).matches()
    }

    // End validation Email and Password
}
