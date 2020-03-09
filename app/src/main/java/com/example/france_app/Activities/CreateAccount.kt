@file:Suppress("DEPRECATION")

package com.example.france_app.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.example.france_app.goToActivity
import com.example.france_app.toast
import com.example.france_app.validate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import java.util.regex.Pattern


class CreateAccount : AppCompatActivity() {
    // Variable for google


    private val TAG = "CreateAccount"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

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

//        et_dateNaissance.validate {
//            et_dateNaissance.error =
//                if (isValideBirthday( et_dateNaissance.text.toString() ) ) null else "Votre  date de naissance doit etre sous le format DD/MM/YYYY"
//        }

        //End validation of Email and Password
    }

    //Creation Compte

    private fun createAccount() {

        val firstName = et_first_name.text.toString()
        val lastName = et_last_name.text.toString()
        val dateNaissance = et_dateNaissance.text.toString()
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

//    private fun isValideBirthday(birthday: String) : Boolean{
//        val pattern : Pattern
//        val BIRTHDAY_PATTERN = "dd/MM/yyyy"
//        pattern = Pattern.compile(BIRTHDAY_PATTERN)
//        return pattern.matcher(birthday).matches()
//    }

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
