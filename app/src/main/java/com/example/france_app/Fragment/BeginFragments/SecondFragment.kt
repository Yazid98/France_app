package com.example.france_app.Fragment.BeginFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.france_app.Activities.LoginActivity
import com.example.france_app.R

/**
 * A simple [Fragment] subclass.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_second, container, false)

        val begin = view.findViewById<Button>(R.id.button_commencer)

        begin.setOnClickListener {
            val intent = Intent(activity!!, LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}
