package com.example.france_app.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.france_app.Fragment.LinksFragment
import com.example.france_app.Fragment.ProceduresFragment
import com.example.france_app.Fragment.ProfileFragment
import com.example.france_app.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_.*

class HomeActivity : AppCompatActivity() {

    //private lateinit var profilFragment: ProfileFragment
    //private lateinit var procedureFragment: ProceduresFragment
    //private lateinit var linksFragment: LinksFragment

    // private lateinit var adapter: PagerAdapter
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)
        mAuth = FirebaseAuth.getInstance()

        val profilFragment = ProfileFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, profilFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, profilFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();true
                }

                R.id.list -> {
                    val procedureFragment = ProceduresFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, procedureFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();true
                }

                R.id.liens -> {
                    val linksFragment = LinksFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, linksFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();true
                }
                else -> true
            }
        }

    }


    //The user will connect automatically if he already signIn
    // otherwise he will go to the beginning of the app
//    override fun onStart() {
//        super.onStart()
//        val user = FirebaseAuth.getInstance().currentUser
//        if (user == null) {
//            goToActivity<Beginning>()
//            finish()
//        }
//    }
//
//    //Indique le pager adpater avec lequel on travaille
//    private fun getPagerAdapter(): PagerAdapter {
//        adapter = PagerAdapter(supportFragmentManager)
//        adapter.addFragment(LinksFragment())
//        adapter.addFragment(ProfileFragment())
//        adapter.addFragment(ProceduresFragment())
//
//        return adapter
//    }

    //La page du PageAdapter à afficher ie le fragment
//    private fun setUpViewPager(adapter: PagerAdapter) {
//        viewPager.adapter = adapter
//
//        //Afficher le Fragment en fonction du scroll sur le page Adapter ou du clic sur le fragment
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {}
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//            override fun onPageSelected(position: Int) {
//                if (prevBottomSelected == null) {
//                    bottomNavigation.menu.getItem(0).isChecked = false
//                } else {
//                    prevBottomSelected!!.isChecked = false
//                }
//                bottomNavigation.menu.getItem(position).isChecked = true
//                prevBottomSelected = bottomNavigation.menu.getItem(position)
//            }
//        })
//    }

}

//Deconnexion_Button
//        logout_button.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            Toast.makeText(
//                this@HomeActivity,
//                "Deconnexion !",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            goToActivity<LoginActivity>()
//        }
//End-Deconnexion_Button