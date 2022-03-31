package com.example.bmimolotkov.ui.weights

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmimolotkov.R
import com.example.bmimolotkov.databinding.FragmentWeightsBinding
import com.example.bmimolotkov.database.DatabaseApp
import com.example.bmimolotkov.database.Weight
import com.example.bmimolotkov.database.Prefs
import com.example.bmimolotkov.databinding.AddWeightBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.lang.NumberFormatException

class WeightsFragment : Fragment(R.layout.fragment_weights) {

    lateinit var binding: FragmentWeightsBinding

    private lateinit var viewModel: WeightsViewModel

    private var weightsAdapter: WeightsAdapter? = null

    private var weightDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            this, WeightsViewModelFactory(
                DatabaseApp.getInstance(requireContext()).getWeightDao(),
                createPrefs()
            )
        ).get(WeightsViewModel::class.java)

        binding = FragmentWeightsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weightsAdapter = WeightsAdapter(
            viewModel::deleteWeight,
            this::editWeight
        )
        binding.weights.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weightsAdapter
        }

        viewModel.weights.observe(viewLifecycleOwner, { weight ->
            weightsAdapter?.setWeights(weight)
        })

        binding.addWeight.setOnClickListener {
            createWeightDialog(null)
            weightDialog?.show()
        }

        binding.addTestWeight.setOnClickListener {
            viewModel.addTestDatas()
        }
    }

    private fun editWeight(weight: Weight) {
        createWeightDialog(weight)
        weightDialog?.show()
    }

    private fun createWeightDialog(currentWeight: Weight?) {
        val dialogView = AddWeightBinding.inflate(layoutInflater)
        currentWeight?.let {
            dialogView.weightEditText.append(String.format("%.1f", it.weight))
            dialogView.weightTitle.text = getString(R.string.edit_weight)
        }

        weightDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                val weight = try {
                    dialogView.weightEditText.text.toString().toFloat()
                } catch (exception: NumberFormatException) {
                    -1F
                }

                when {
                    weight <= 0F -> toast(getString(R.string.form_weight_error))
                    currentWeight != null -> viewModel.editWeight(currentWeight, weight)
                    else -> viewModel.addWeight(weight)
                }
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, _ ->
                dialog.cancel()
            }
            .setOnDismissListener {
                it.dismiss()
            }
            .create()
    }

    private fun createPrefs() = Prefs(
        requireContext().getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE),
        Gson()
    )

    private fun toast(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}
