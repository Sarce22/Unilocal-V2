package com.eam.unilocalv2.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment

class ImagenesLugarFragment : Fragment() {

    companion object{
        fun newInstance(codigoLugar: Int):ImagenesLugarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = ImagenesLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}