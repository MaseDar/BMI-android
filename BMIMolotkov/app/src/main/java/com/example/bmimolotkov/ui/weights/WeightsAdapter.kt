package com.example.bmimolotkov.ui.weights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bmimolotkov.R
import com.example.bmimolotkov.dateTimeFormat
import com.example.bmimolotkov.database.Weight
import com.example.bmimolotkov.databinding.ListItemWeightBinding

class WeightsAdapter(
    private val deleteOnClickListener: (Weight) -> Unit,
    private val editOnClickListener: (Weight) -> Unit
) : RecyclerView.Adapter<WeightsAdapter.WeightHolder>() {

    private val weights = mutableListOf<Weight>()

    fun setWeights(newWeights: List<Weight>) {
        weights.apply {
            clear()
            addAll(newWeights)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightHolder {
        val binding = ListItemWeightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeightHolder(binding)
    }

    override fun onBindViewHolder(holder: WeightHolder, position: Int) =
        holder.bind(weights[position])

    override fun getItemCount() = weights.size

    inner class WeightHolder(private val binding: ListItemWeightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weight: Weight) {
            binding.weight.text =
                itemView.context.getString(R.string.item_weight_value, weight.weight)
            binding.bodyIndex.text = String.format("%.2f", weight.indexWeight)
            binding.date.text = weight.dateOfWeight.dateTimeFormat()
            binding.difference.text =
                itemView.context.getString(R.string.item_weight_value, weight.difference)

            binding.editButton.setOnClickListener { editOnClickListener(weight) }

            binding.deleteButton.setOnClickListener { deleteOnClickListener(weight) }
        }
    }
}