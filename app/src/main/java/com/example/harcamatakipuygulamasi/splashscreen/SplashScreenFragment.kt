package com.example.harcamatakipuygulamasi.splashscreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.harcamatakipuygulamasi.R
import java.util.*
import kotlin.concurrent.schedule


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timer().schedule(3000) {
            lifecycleScope.launchWhenResumed {
                if (onBoardingFinish()) {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                }
                else {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
                }
            }
        }
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
        private fun onBoardingFinish(): Boolean {
            val sharedPref =
                requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            return sharedPref.getBoolean("Finished", false)

        }

}
