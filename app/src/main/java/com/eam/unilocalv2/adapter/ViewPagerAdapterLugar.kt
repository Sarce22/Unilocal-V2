package com.eam.unilocalv2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eam.unilocalv2.fragmentos.ComentarFragment
import com.eam.unilocalv2.fragmentos.ComentariosLugarFragment
import com.eam.unilocalv2.fragmentos.ImagenesLugarFragment
import com.eam.unilocalv2.fragmentos.InfoLugarFragment

class ViewPagerAdapterLugar(var fragment: FragmentActivity, var codigoLugar: String, var pos1Fragment: Int): FragmentStateAdapter(fragment) {

    companion object{
        var cod: Int = -1
        lateinit var fragmentoPos1: Fragment
    }

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        cod = codigoLugar
        if(pos1Fragment!=1){
            fragmentoPos1 = ComentarFragment.newInstance(cod)
        }else{
            fragmentoPos1 = ComentariosLugarFragment.newInstance(cod)
        }
        return when(position){
            0 -> InfoLugarFragment.newInstance(codigoLugar)
            1 -> fragmentoPos1
            else -> ImagenesLugarFragment.newInstance(codigoLugar)
        }
    }
}