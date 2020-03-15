package com.example.france_app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.france_app.R


/**
 * A simple [Fragment] subclass.
 */
class ProceduresFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_procedures, container, false)


        val assurance = rootView.findViewById<Button>(R.id.assuranceMaladie)
        assurance.setOnClickListener {
            Toast.makeText(activity!!, "List clicked", Toast.LENGTH_SHORT).show()
            val fragment = ListTacheSecuFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

//            val POSITION = 3
//            viewPager.setCurrentItem(POSITION, true);
//            (activity as HomeActivity?)!!.selectFragment(3)
        }

        return rootView
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//    }
}