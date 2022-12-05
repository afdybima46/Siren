package com.example.siren.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(fragment: Fragment, data: Bundle) :
    FragmentStateAdapter(fragment) {

        private var fragmentBundle: Bundle = data

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = RecommendedFragment()
            1 -> fragment = FavoriteFragment()
        }
        fragment?.arguments = fragmentBundle
        return fragment as Fragment
    }
}