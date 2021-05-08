package com.example.harcamatakipuygulamasi.userprofile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.harcamatakipuygulamasi.databinding.UserProfileFragmentBinding

class UserProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = UserProfileFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val viewModelFactory = UserProfileViewModelFactory(application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(UserProfileViewModel::class.java)

        viewModel.userName.observe(viewLifecycleOwner, { newUserName ->
            binding.editTextUserNameFragmentNameInput.setText(newUserName)
        })

        viewModel.userGender.observe(viewLifecycleOwner, { newUserGender ->
            if(newUserGender == 1)
                binding.radioGroupUserNameFragment.id = binding.radioButtonUserNameFragmentMale.id
            else if(newUserGender == 0)
                binding.radioGroupUserNameFragment.id = binding.radioButtonUserNameFragmentFemale.id
        })

        binding.buttonUserNameFragmentSave.setOnClickListener {
            val userName = binding.editTextUserNameFragmentNameInput.text.toString()
            val userGender = when(binding.radioGroupUserNameFragment.checkedRadioButtonId)
            {
                binding.radioButtonUserNameFragmentMale.id -> 1
                binding.radioButtonUserNameFragmentFemale.id -> 0
                else -> -1
            }
            viewModel.saveUserProfile(userName, userGender)
        }

        viewModel.checkGoHome.observe(viewLifecycleOwner, { newCheckGoHome ->
            if(newCheckGoHome == true) {
                this.findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToHomeFragment())
                viewModel.saveUserProfileComplete()
            }
        })

        return binding.root
    }
}