@file:Suppress("RedundantSamConstructor")

package com.example.france_app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


@Suppress("UNUSED_VARIABLE", "SpellCheckingInspection")
class RegistrationActivity : AppCompatActivity() {

    //lateinit variables are instances of Firebase which permit to SignUp
     lateinit var auth: FirebaseAuth
     lateinit var emailEt: EditText
     lateinit var passwordEt: EditText
     lateinit var signUpButton: Button

     lateinit var firstname : String
     lateinit var lastname : String
     lateinit var age : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_)

        // To display the image
        val mImageView = findViewById<ImageView>(R.id.logo1)
        mImageView.setImageResource(R.drawable.image_logo1)


        auth = FirebaseAuth.getInstance()

        //Registration
        emailEt = findViewById(R.id.signUp_email)
        passwordEt = findViewById(R.id.SignUp_password)

        firstname = findViewById<EditText>(R.id.signUp_prenom).toString()
        lastname = findViewById<EditText>(R.id.signUp_nom).toString()
        age = findViewById<EditText>(R.id.signUp_nom).toString()
        signUpButton = findViewById(R.id.validateRegistration)


        var email: String = emailEt.text.toString()
        var password: String = passwordEt.text.toString()


        //Here we have to cast variables to text format so Firebase will save them.


            signUpButton.setOnClickListener {
                //If there are no input
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "Remplissez tout les champs SVP", Toast.LENGTH_LONG).show()
                } else {

                    //This is a function of firebase to SignUp with email and password
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                        OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Vous êtes bien enregistré", Toast.LENGTH_LONG).show()
                                //savehero()
                                val newActivity1 = Intent(this, Home_Activity::class.java)
                                startActivity(newActivity1)
                                finish()
                            } else {
                                Toast.makeText(this, "Enregistrement non validé", Toast.LENGTH_LONG).show()
                            }
                        })

                }
            }
    }

    private fun savehero() {
        val firstname = firstname
        val lastname = lastname
        val age = age

        if ( TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(age) ){
            Toast.makeText(this, "Remplissez tous les champs svp", Toast.LENGTH_LONG).show()
        }
            val ref = FirebaseDatabase.getInstance().getReference("users")
            val heroId = ref.push().key
            val users = Users(heroId, firstname, lastname, age)
            ref.child(heroId.toString()).setValue(users).addOnCompleteListener {
                Toast.makeText(this, "Utilisateur bien enregistrer", Toast.LENGTH_LONG).show()
            }
    }
}
