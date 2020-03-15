package com.example.france_app.Fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.france_app.Activities.LoginActivity
import com.example.france_app.R
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //To introduce the cursor when the user click on the editText

        val firstName: EditText = view.findViewById(R.id.firstNameUser)
        val lastName: EditText = view.findViewById(R.id.lastNameUser)

        firstName.setOnClickListener {
            firstName.isCursorVisible = true
        }

        lastName.setOnClickListener {
            lastName.isCursorVisible = true
        }

        //End of cursor introducing

        //To select calendar when the user wants to choose his birthday

        val date = view.findViewById<TextView>(R.id.birthdayDateUser)
        val calendar = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                date.text = sdf.format(calendar.time)
            }

        date.setOnClickListener {
            DatePickerDialog(
                view.context, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        //End of the Calendar selection

        val deconnexion = view.findViewById<Button>(R.id.button_deconnexion)
        deconnexion.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(activity, "Deconnexion!", Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}