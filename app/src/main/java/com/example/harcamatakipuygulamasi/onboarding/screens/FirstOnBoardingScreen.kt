package com.example.harcamatakipuygulamasi.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.databinding.FragmentFirstOnBoardingScreenBinding
import kotlinx.parcelize.Parcelize


class FirstOnBoardingScreen : Fragment() {

    private lateinit var buttonNext : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFirstOnBoardingScreenBinding = FragmentFirstOnBoardingScreenBinding.inflate(
            inflater,
            container,
            false
        )

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2_viewPagerFragment)

        binding.buttonFirstOnBoardingScreenFragmentNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }
}