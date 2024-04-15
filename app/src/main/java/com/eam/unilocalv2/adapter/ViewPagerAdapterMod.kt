package com.eam.unilocalv2.adapter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eam.unilocalv2.R
import com.eam.unilocalv2.fragmentos.ModLugaresFragment
import com.eam.unilocalv2.fragmentos.ModRegistroLugaresFragment

class ViewPagerAdapterMod(var fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ModLugaresFragment()
            else -> ModRegistroLugaresFragment()
        }
    }
}