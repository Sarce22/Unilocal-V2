package com.eam.unilocalv2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eam.unilocalv2.fragmentos.ComentariosGestionarLugarFragment
import com.eam.unilocalv2.fragmentos.ImagenesLugarFragment
import com.eam.unilocalv2.fragmentos.InfoLugarFragment

class ViewPagerAdapterGestionarLugar(var fragment: FragmentActivity, var codigoLugar: Int): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> InfoLugarFragment.newInstance(codigoLugar)
            1 -> ComentariosGestionarLugarFragment.newInstance(codigoLugar)
            else -> ImagenesLugarFragment.newInstance(codigoLugar)
        }
    }
}