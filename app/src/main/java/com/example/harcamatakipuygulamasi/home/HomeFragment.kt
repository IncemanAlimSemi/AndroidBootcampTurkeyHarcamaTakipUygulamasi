package com.example.harcamatakipuygulamasi.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.adapter.SpendingAdapter
import com.example.harcamatakipuygulamasi.adapter.SpendingClickTracker
import com.example.harcamatakipuygulamasi.adapter.TypeOfMoneyTracker
import com.example.harcamatakipuygulamasi.database.SpendingDatabase
import com.example.harcamatakipuygulamasi.databinding.HomeFragmentBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var adapter: SpendingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(this.activity).application
        val spendingDatabaseDAO = SpendingDatabase.getInstance(application).spendingDatabaseDAO

        val binding = HomeFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModelFactory = HomeViewModelFactory(spendingDatabaseDAO, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val currentTypeOfMoney = HomeFragmentArgs.fromBundle(requireArguments()).currentTypeOfMoney
        viewModel.setCurrentTypeOfMoney(currentTypeOfMoney)

        binding.radioGroupHomeFragmentMidConstraintLayout.check(when(currentTypeOfMoney) {
            1 -> binding.radioButtonSpendingAddFragmentEuro.id
            2 -> binding.radioButtonSpendingAddFragmentDollar.id
            3 -> binding.radioButtonSpendingAddFragmentSterling.id
            else -> binding.radioButtonSpendingAddFragmentTL.id
        })

        viewModel.status.observe(viewLifecycleOwner, { newStatus ->
            if(newStatus == true) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.con_fail), Snackbar.LENGTH_SHORT).show()
                viewModel.statusComplete()
            }
        })

        adapter = SpendingAdapter(SpendingClickTracker {
            viewModel.onSpending(it)},
            TypeOfMoneyTracker {
                viewModel.totalSpending(it)},
            viewModel.getCurrentTypeOfMoney(),
            viewModel.getCurrentSpendingRate())

        binding.recyclerViewHomeFragmentCoordinatorLayoutSpending.adapter = adapter

        viewModel.allSpending.observe(viewLifecycleOwner, { newAllSpending ->
            newAllSpending?.let {
                adapter.submitList(newAllSpending)
                var total = 0f
                newAllSpending.forEach { i ->
                    total += i.theMoneySpent
                }
                viewModel.totalSpending(total)
            }
        })

        viewModel.userName.observe(viewLifecycleOwner, { newUserName ->
            binding.textViewHomeFragmentTopConstraintLayoutUserName.text = newUserName
        })

        viewModel.userGender.observe(viewLifecycleOwner, { newUserGender ->
            binding.textViewHomeFragmentTopConstraintLayoutUserGender.text = when(newUserGender) {
                0 -> " Hanım"
                1 -> " Bey"
                else -> ""
            }
        })

        viewModel.totalSpending.observe(viewLifecycleOwner, { newTotalSpending ->
            binding.textViewHomeFragmentTopConstraintLayoutSpendingMoney.text = newTotalSpending
        })

        viewModel.currentTypeOfMoney.observe(viewLifecycleOwner, {
            adapter.changeTypeOfMoney(viewModel.getCurrentTypeOfMoney(), viewModel.getCurrentSpendingRate())
        })

        binding.radioGroupHomeFragmentMidConstraintLayout.setOnCheckedChangeListener { _, checkedID ->
            when(checkedID) {
                binding.radioButtonSpendingAddFragmentTL.id -> viewModel.setCurrentTypeOfMoney(0)
                binding.radioButtonSpendingAddFragmentEuro.id -> viewModel.setCurrentTypeOfMoney(1)
                binding.radioButtonSpendingAddFragmentDollar.id -> viewModel.setCurrentTypeOfMoney(2)
                binding.radioButtonSpendingAddFragmentSterling.id -> viewModel.setCurrentTypeOfMoney(3)
            }
        }

        viewModel.goSpendingDetail.observe(viewLifecycleOwner, { newGoSpendingDetail ->
            if(newGoSpendingDetail != null) {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSpendingDetailFragment(
                    newGoSpendingDetail,
                    viewModel.getCurrentTypeOfMoney()))
                viewModel.onSpendingComplete()
            }
        })

        binding.constraintLayoutHomeFragmentTopConstraintLayout.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment())
        }

        binding.textViewHomeFragmentTopConstraintLayoutUserName.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment())
        }

        binding.textViewHomeFragmentTopConstraintLayoutUserGender.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment())
        }

        binding.floatingActionButtonHomeFragmentCoordinatorLayoutAddSpending.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddSpendingFragment())
        }

        return binding.root
    }
}
