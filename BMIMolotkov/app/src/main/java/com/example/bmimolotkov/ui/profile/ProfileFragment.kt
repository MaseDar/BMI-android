package com.example.bmimolotkov.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bmimolotkov.R
import com.example.bmimolotkov.databinding.FragmentProfileBinding
import com.example.bmimolotkov.database.DatabaseApp
import com.example.bmimolotkov.database.Prefs
import com.google.gson.Gson

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(
                DatabaseApp.getInstance(requireContext()).getWeightDao(),
                createPrefs()
            )
        ).get(ProfileViewModel::class.java)

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastWeight.observe(viewLifecycleOwner, {
            binding.name.text = viewModel.user?.name
            binding.userHeight.text = getString(R.string.profile_user_height, viewModel.user?.height)

            if (it != null) {
                binding.userWeight.text = getString(R.string.profile_user_weight, it.weight)
                binding.userBodyIndex.text = getString(R.string.profile_body_index, it.indexWeight)
            } else {
                binding.userWeight.visibility = View.GONE
                binding.userBodyIndex.visibility = View.GONE
            }

        })
    }

    private fun createPrefs() = Prefs(
        requireContext().getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE),
        Gson()
    )
}