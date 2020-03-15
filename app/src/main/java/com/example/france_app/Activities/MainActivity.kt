package com.example.france_app.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.france_app.Fragment.BeginFragments.FirstFragment
import com.example.france_app.Fragment.BeginFragments.SecondFragment
import com.example.france_app.R
import kotlinx.android.synthetic.main.activity_main.*
import me.relex.circleindicator.CircleIndicator

//This is the first activity that is load when the user start the application and don't have an account
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPager(viewPager)
        val firstFragment = FirstFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, firstFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

    }


    private fun setUpViewPager(viewPager: ViewPager) {
        val viewPagerAdapter = com.example.france_app.adapter.PagerAdapter(supportFragmentManager)
        val indicator = findViewById<CircleIndicator>(R.id.indicator)

        var firstFragment = FirstFragment()
        var secondFragment = SecondFragment()

        viewPagerAdapter.addFragment(firstFragment)
        viewPagerAdapter.addFragment(secondFragment)

        viewPager.adapter = viewPagerAdapter
        indicator.setViewPager(viewPager)
    }
}
