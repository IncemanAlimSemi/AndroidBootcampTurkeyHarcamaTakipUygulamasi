package com.example.harcamatakipuygulamasi.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.databinding.FragmentSecondOnBoardingScreenBinding
import com.example.harcamatakipuygulamasi.databinding.FragmentViewPagerBinding
import com.example.harcamatakipuygulamasi.onboarding.screens.FirstOnBoardingScreen
import com.example.harcamatakipuygulamasi.onboarding.screens.SecondOnBoardingScreen
import com.example.harcamatakipuygulamasi.onboarding.screens.ThirdOnBoardingScreen


class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentViewPagerBinding = FragmentViewPagerBinding.inflate(
            inflater,
            container,
            false
        )

        val fragmentList = arrayListOf(
            FirstOnBoardingScreen(),
            SecondOnBoardingScreen(),
            ThirdOnBoardingScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager2ViewPagerFragment.adapter = adapter

        return binding.root
    }
}