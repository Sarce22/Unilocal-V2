package com.eam.unilocalv2.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.eam.unilocalv2.R
import com.eam.unilocalv2.databinding.FragmentImagenBinding


class ImagenFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var binding: FragmentImagenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("url_img")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagenBinding.inflate(inflater, container, false)

        Glide.with( requireContext() )
            .load(param1)
            .into(binding.imgUrl)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ImagenFragment().apply {
                arguments = Bundle().apply {
                    putString("url_img", param1)
                }
            }
    }
}