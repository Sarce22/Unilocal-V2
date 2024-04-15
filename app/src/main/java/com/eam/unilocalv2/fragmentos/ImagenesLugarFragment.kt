package com.eam.unilocalv2.fragmentos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.eam.unilocalv2.R

class ImagenesLugarFragment : Fragment() {

    companion object{
        fun newInstance(codigoLugar:Int):ImagenesLugarFragment{
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            val fragmento = ImagenesLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}