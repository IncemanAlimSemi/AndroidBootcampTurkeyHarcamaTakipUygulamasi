package com.example.harcamatakipuygulamasi.spendingdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.database.SpendingDatabase
import com.example.harcamatakipuygulamasi.databinding.SpendingDetailFragmentBinding

class SpendingDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = SpendingDetailFragmentBinding.inflate(
            inflater,
            container,
            false)

        val application = requireNotNull(this.activity).application
        val spendingDatabaseDetailDAO = SpendingDatabase.getInstance(application).spendingDatabaseDAO
        val spending = SpendingDetailFragmentArgs.fromBundle(requireArguments()).selectedSpending
        val moneyType = SpendingDetailFragmentArgs.fromBundle(requireArguments()).selectedTypeOfMoney

        val viewModelFactory = SpendingDetailViewModelFactory(spendingDatabaseDetailDAO, application, spending, moneyType)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SpendingDetailViewModel::class.java)

        binding.imageButtonSpendingDetailFragment.setOnClickListener {
            viewModel.goBackToHome()
        }

        binding.imageViewSpendingDetailFragment.setImageResource(when(spending.typeOfSpending) {
            1 -> R.drawable.bill
            2 -> R.drawable.home
            else -> R.drawable.bags_other
        })

        binding.textViewSpendingDetailFragmentDescription.text = spending.descriptionOfSpending

        viewModel.spendingMoney.observe(viewLifecycleOwner, {
            binding.textViewSpendingDetailFragmentMoney.text = it
        })

        binding.buttonSpendingDetailFragmentDelete.setOnClickListener {
            viewModel.delete()
        }

        viewModel.checkGoHome.observe(viewLifecycleOwner, {
            if(it == true) {
                this.findNavController().navigate(SpendingDetailFragmentDirections.actionSpendingDetailFragmentToHomeFragment(moneyType))
            }
        })

        return binding.root
    }
}