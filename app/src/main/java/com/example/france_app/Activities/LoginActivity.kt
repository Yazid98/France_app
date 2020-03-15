@file:Suppress("DEPRECATION")

package com.example.france_app.Activities

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.france_app.R
import com.example.france_app.goToActivity
import com.example.france_app.toast
import com.example.france_app.validate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern


@Suppress("RedundantSamConstructor")
class LoginActivity : AppCompatActivity() {

    //Variables declarations
    private lateinit var firebaseAuth: FirebaseAuth
    private var isShowPass = false
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    //Variables declarations for google
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        //Google Registration

        configureGoogleSignIn()
        setupUI()
        // End Google Registration and continue after OnCreate method

        //Button Create Account
        create_account.setOnClickListener {
            goToActivity<CreateAccount>()
        }



        //For show and hide the password
        ivShowPassword.setOnClickListener {
            isShowPass = !isShowPass
            showPassword(isShowPass)
        }
        showPassword(isShowPass)

        //Login button
        emailEt = findViewById(R.id.login_email)
        passwordEt = findViewById(R.id.login_password)
        auth = FirebaseAuth.getInstance()
        login_button.setOnClickListener {

            val email: String = emailEt.text.toString()
            val password: String = passwordEt.text.toString()
            if (!isValideEmail(email) || !isValidePassword(password)) {
                toast("Veuillez bien renseigner tout les champs")
            } else {
                loginByEmailPassword(email, password)
            }
        }


        //Reset password button
        reset_password_button.setOnClickListener {
            goToActivity<ResetPassword>()
            finish()
        }

        //This is to show error while type wrong inputs(email and password)
        login_email.validate {
            login_email.error = if (isValideEmail(it)) null else "Email non valide"
        }
        login_password.validate {
            login_password.error =
                if (isValidePassword(it)) null else "Votre mot de passe doit contenir 8 caractères au minimums dont une lettre majuscule, une lettre miniscule, et un chiffre entre 0 et 9"
        }

        //To introduce the cursor when the user click on the editText
        login_email.setOnClickListener {
            login_email.isCursorVisible = true
        }
        login_password.setOnClickListener {
            login_password.isCursorVisible = true
        }

    }


    //This method permit the logging by email and password
    private fun loginByEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    //We look if the user confirmed his email
                    if (auth.currentUser!!.isEmailVerified) {
                        toast("Connected")
                        goToActivity<HomeActivity>()
                    } else {
                        toast("Verify your email before login")
                    }
                } else if (task.isCanceled) {
                    toast("Connexion failed")
                }
            }
    }

    //This method permit tho show the password to the user while entered it
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

    //if email is valid
    private fun isValideEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches() && !login_email.text.isNullOrEmpty()
    }

    //if password is valid
    private fun isValidePassword(password: String): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        return pattern.matcher(password).matches() && !login_password.text.isNullOrEmpty()
    }

    //Google SignIn continue

    //The method is used to request the user data required by your app such as the users’ ID and basic profile information, email address and Id token
    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    //Add the setupUI() method to set up the OnClickListener for our login button. This method gets called in the OnCreate() method:
    private fun setupUI() {
        login_sign_in_google.setOnClickListener {
            signIn()
        }
    }

    //Next, we add a singIn() method which will be called when the user presses on the log in button. The user will be prompted to select an authentication account.
    // The signInIntent is used to handle the sign in process and for starting the intent the startActivityForResult() is used.
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //Once done the onActivityResult() is called in which the selected google account is retrieved and sent to Firebase for authentication.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                goToActivity<HomeActivity>()
            } else {
                toast("Google sign in failed:(")
            }
        }
    }

    //End Google SignIn
}