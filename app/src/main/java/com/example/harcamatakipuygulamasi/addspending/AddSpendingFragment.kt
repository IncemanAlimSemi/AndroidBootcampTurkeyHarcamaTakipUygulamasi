package com.example.harcamatakipuygulamasi.addspending

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.harcamatakipuygulamasi.database.SpendingDatabase
import com.example.harcamatakipuygulamasi.databinding.AddSpendingFragmentBinding

class AddSpendingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(this.activity).application
        val spendingDatabaseDAO = SpendingDatabase.getInstance(application).spendingDatabaseDAO

        val binding = AddSpendingFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModelFactory = AddSpendingViewModelFactory(spendingDatabaseDAO, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(AddSpendingViewModel::class.java)

        binding.buttonSpendingAddFragmentAdd.setOnClickListener {
            val spendingType = when(binding.radioGroupSpendingAddFragmentSpendingType.checkedRadioButtonId) {
                binding.radioButtonSpendingAddFragmentBill.id -> 1
                binding.radioButtonSpendingAddFragmentRent.id -> 2
                binding.radioButtonSpendingAddFragmentOther.id -> 3
                else -> -1
            }
            val description = binding.editTextSpendingAddFragmentDescription.text.toString()
            val moneyType = when(binding.radioGroupSpendingAddFragmentMoneyType.checkedRadioButtonId) {
                binding.radioButtonSpendingAddFragmentEuro.id -> 1
                binding.radioButtonSpendingAddFragmentDollar.id -> 2
                binding.radioButtonSpendingAddFragmentSterling.id -> 3
                else -> 0
            }
            val spendingText = binding.editTextSpendingAddFragmentSpending.text.toString()
            viewModel.createSpending(spendingType, description, moneyType, spendingText)
        }

        viewModel.checkGoHome.observe(viewLifecycleOwner, { newCheckGoHome ->
            if(newCheckGoHome == true) {
                this.findNavController().navigate(AddSpendingFragmentDirections.actionAddSpendingFragmentToHomeFragment())
                viewModel.checkGoHomeCompleted()
            }
        })

        return binding.root
    }
}