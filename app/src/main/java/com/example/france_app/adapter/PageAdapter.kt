package com.example.france_app.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    //On crée une liste de fragment
    private val fragmentList = ArrayList<Fragment>()

    //Fragment  du pageAdapter à afficher selectionné par l'utilisateur
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    //Nombre d'items(fragments) dans le page adapter
    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}