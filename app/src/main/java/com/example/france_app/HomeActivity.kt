package com.example.france_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var notificationFragment: NotificationFragment
    lateinit var accountFragment: AccountFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.mainNav)


        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainFrame, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_notif -> {
                    notificationFragment = NotificationFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainFrame, notificationFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_account -> {
                    accountFragment = AccountFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainFrame, accountFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }


//_____________________________________________________-Deconnexion-Button________________________________________________________________________________
        /*logout_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                this@HomeActivity,
                "Deconnexion !",
                Toast.LENGTH_SHORT
            ).show()

            newActivity()
        }*/
        //_____________________________________________________-End-Deconnexion-Button________________________________________________________________________________
    }

    /*private fun newActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }*/


}