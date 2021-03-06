package com.example.france_app.Activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.france_app.Fragment.LinksFragment
import com.example.france_app.Fragment.ProceduresFragment
import com.example.france_app.Fragment.ProfileFragment
import com.example.france_app.R
import com.example.france_app.adapter.PagerAdapter
import com.example.france_app.goToActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_.*

//This is the first activity that is load when the user start the application and already have an account

class HomeActivity : AppCompatActivity() {

    // private lateinit var adapter: PagerAdapter
    private var mAuth: FirebaseAuth? = null
    private var prevBottomSelected: MenuItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)
        mAuth = FirebaseAuth.getInstance()

        //For switching with the bottom navigation using viewPager
        setUpViewPager(viewPager)
        val proceduresFragment = ProceduresFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, proceduresFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile -> {
                    viewPager.currentItem = 0;true
                }

                R.id.list -> {
                    viewPager.currentItem = 1; true
                }

                R.id.liens -> {
                    viewPager.currentItem = 2; true
                }

                else -> false
            }
        }

        //The addOnPageChangeListener implement method that are used for the pages.
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                //To stop the swiping when we are at the end of a page
                if (prevBottomSelected != null) {
                    prevBottomSelected!!.isChecked = false
                } else {
                    bottom_navigation.menu.getItem(0).isChecked = false
                }
                bottom_navigation.menu.getItem(position).isChecked = true
                prevBottomSelected = bottom_navigation.menu.getItem(position)

            }
        })

    }

//    fun selectFragment(position: Int){
//
//        viewPager.setCurrentItem(position, true);
//        // true is to animate the transaction
//    }

    //The setUp of the viewPager will adapt the viewPager by dint of the pagerAdapter
    private fun setUpViewPager(viewPager: ViewPager) {

        val viewPagerAdapter = PagerAdapter(supportFragmentManager)

        var profileFragment = ProfileFragment()
        val proceduresFragment = ProceduresFragment()
        val linksFragment = LinksFragment()

        viewPagerAdapter.addFragment(profileFragment)
        viewPagerAdapter.addFragment(proceduresFragment)
        viewPagerAdapter.addFragment(linksFragment)

        viewPager.adapter = viewPagerAdapter
    }

    //This method will permit to the user to connect automatically if he already signIn
    // otherwise he will go to the beginning of the app
    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            goToActivity<MainActivity>()
            finish()
        }
    }

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