@file:Suppress("DEPRECATION")

package com.example.france_app

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccount : AppCompatActivity() {

    private val TAG = "CreateAccount"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btn_register.setOnClickListener {
            createAccount()
        }
    }


    private fun createAccount() {

        val firstName = et_first_name.text.toString()
        val lastName = et_last_name.text.toString()
        val dateNaissance = et_dateNaissance.text.toString()
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        when {
            TextUtils.isEmpty(firstName) -> Toast.makeText(
                this,
                "Enter first name",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(lastName) -> Toast.makeText(
                this,
                "Enter last name",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(dateNaissance) -> Toast.makeText(
                this,
                "Enter your birthday name",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(email) -> Toast.makeText(
                this,
                "Enter email address",
                Toast.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(password) -> Toast.makeText(
                this,
                "Enter your password",
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
                                    newActivity()
                                    Toast.makeText(
                                        this,
                                        "Email verification sent!",
                                        Toast.LENGTH_LONG
                                    ).show()
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

    /*private fun verifyEmail() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val mUser = mAuth.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@CreateAccount,
                        "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@CreateAccount,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }*/

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

        //newActivity()
    }

    private fun newActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}
