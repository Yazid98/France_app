@file:Suppress("RedundantSamConstructor")

package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


@Suppress("UNUSED_VARIABLE", "SpellCheckingInspection", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE",
    "UNUSED_PARAMETER"
)
class RegistrationActivity(var users: Users) : AppCompatActivity() {

    var firstname = findViewById<EditText>(R.id.signUp_prenom)
    var lastname = findViewById<EditText>(R.id.signUp_nom)
    var age = findViewById<EditText>(R.id.signUp_Age)
    var email = findViewById<EditText>(R.id.signUp_email)
    var password = findViewById<EditText>(R.id.SignUp_password)
    var signUpBtn = findViewById<Button>(R.id.validateRegistration)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_)


        //For Firebase
        var database: FirebaseDatabase
        var mdatabase: DatabaseReference


        signUpBtn.setOnClickListener {

            val email: String = email.text.toString()
            val password: String = password.text.toString()

            val firstname: String = firstname.text.toString()
            val lastname: String = lastname.text.toString()
            val age: String = age.text.toString()

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                var users = Users(email, password, firstname, lastname, age)
                Toast.makeText(this, "Please fill all the gaps", Toast.LENGTH_LONG).show()
            }
            registerUser(email, password)
        }
    }


    private fun registerUser(email: String, password: String) {

        val mAuth: FirebaseAuth
        mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Vous êtes bien enregistré", Toast.LENGTH_LONG).show()
                    updateUI(users)
                }
            })
    }

    @Suppress("JoinDeclarationAndAssignment")
    fun updateUI(currentUser: Users) {
        val mdatabase: DatabaseReference
        mdatabase = FirebaseDatabase.getInstance().reference
        val keyId: String? = mdatabase.push().key
        mdatabase.child(keyId.toString()).setValue(users).addOnCompleteListener {
            Toast.makeText(this, "Vous êtes bien enregistré", Toast.LENGTH_LONG).show()
        }
        val intent = Intent(this@RegistrationActivity, Home_Activity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val USERS = "users"
    }
}
