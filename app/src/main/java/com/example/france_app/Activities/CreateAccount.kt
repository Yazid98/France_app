@file:Suppress("DEPRECATION")

package com.example.france_app.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import java.util.regex.Pattern


class CreateAccount : AppCompatActivity() {

    private val TAG = "CreateAccount"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btn_register.setOnClickListener {
            createAccount()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }

        //Retour au debut de l'appli
        back_to_debut.setOnClickListener {
            goToActivity<debut_app>()
        }

        //Validation des Champs Email et password
        et_email.validate {
            et_email.error =
                if (isValideEmail(et_email.text.toString())) null else "Format de l'email non valide"
        }

        et_password.validate {
            et_password.error =
                if (isValidePassword(et_password.text.toString())) null else "Votre mot de passe doit contenir 8 caractères au minimums dont une lettre majuscule, une lettre miniscule, et un chiffre entre 0 et 9"
        }
    }

    //________________________________________________________________________________________________Creation Compte ________________________________________________________________________________________________________

    private fun createAccount() {

        val firstName = et_first_name.text.toString()
        val lastName = et_last_name.text.toString()
        val dateNaissance = et_dateNaissance.text.toString()
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        when {
            TextUtils.isEmpty(firstName) -> Toast.makeText(
                this,
                "Remplissez tous les champs",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(lastName) -> Toast.makeText(
                this,
                "Remplissez tous les champs",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(dateNaissance) -> Toast.makeText(
                this,
                "Remplissez tous les champs",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(email) -> Toast.makeText(
                this,
                "Remplissez tous les champs",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(password) -> Toast.makeText(
                this,
                "Remplissez tous les champs",
                Toast.LENGTH_SHORT
            ).show()

            else -> {

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
                                    Toast.makeText(
                                        this,
                                        "Email verification sent!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    goToActivity<HomeActivity>()
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

                            //verifyEmail()

                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }
    //_____________________________________Fin Creation Compte____________________________________________________________________________


    //Sauvegarde des informations dans la BDD
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


    //Validation Email et Mot de passe

    private fun isValideEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    //Permet d'imposer un mot de passe spécifiqueSS
    private fun isValidePassword(password: String): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        return pattern.matcher(password).matches()
    }

    // Fin Validation Email et Mot de passe
}
