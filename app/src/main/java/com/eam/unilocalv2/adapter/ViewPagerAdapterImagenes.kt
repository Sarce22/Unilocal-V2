package com.eam.unilocalv2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eam.unilocalv2.fragmentos.ImagenFragment

class ViewPagerAdapterImagenes(var fragment: FragmentActivity, private var imagenes: ArrayList<String>): FragmentStateAdapter(fragment) {
    override fun getItemCount() = imagenes.size

    override fun createFragment(position: Int): Fragment {

        when(position){
            position -> return ImagenFragment.newInstance(imagenes[position])
        }
        return Fragment()
    }
}