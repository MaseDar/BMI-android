package com.example.bmimolotkov.ui.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bmimolotkov.R
import com.example.bmimolotkov.databinding.FragmentStatisticsBinding
import com.example.bmimolotkov.dateFormat
import com.example.bmimolotkov.database.DatabaseApp
import com.example.bmimolotkov.database.Weight
import java.time.LocalDateTime

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    lateinit var binding: FragmentStatisticsBinding

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            this, StatisticsViewModelFactory(
                DatabaseApp.getInstance(requireContext()).getWeightDao()
            )
        ).get(StatisticsViewModel::class.java)

        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastWeekWeights.observe(viewLifecycleOwner, {
            binding.lastWeekPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusWeeks(1).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgWeekWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgWeekBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })

        viewModel.lastMonthWeights.observe(viewLifecycleOwner, {
            binding.lastMonthPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusMonths(1).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgMonthWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgMonthBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })

        viewModel.lastQuarterWeights.observe(viewLifecycleOwner, {
            binding.lastQuarterPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusMonths(3).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgQuarterWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgQuarterBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })

        viewModel.lastYearWeights.observe(viewLifecycleOwner, {
            binding.lastYearPeriod.text = String.format(
                "%s - %s",
                LocalDateTime.now().minusYears(1).dateFormat(),
                LocalDateTime.now().dateFormat()
            )
            binding.avgYearWeight.text = String.format("%.1f", calculateAvgWeight(it))
            binding.avgYearBodyIndex.text = String.format("%.2f", calculateAvgBodyIndex(it))
        })
    }

    private fun calculateAvgWeight(weights: List<Weight>) =
        if (weights.isNotEmpty()) {
            var sumWeight = 0F
            weights.forEach { sumWeight += it.weight }
            sumWeight / weights.size
        } else {
            0F
        }

    private fun calculateAvgBodyIndex(weights: List<Weight>) =
        if (weights.isNotEmpty()) {
            var sumBodyIndex = 0F
            weights.forEach { sumBodyIndex += it.indexWeight }
            sumBodyIndex / weights.size
        } else {
            0F
        }
}
