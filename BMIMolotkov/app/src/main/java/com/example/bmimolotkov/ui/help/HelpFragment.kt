package com.example.bmimolotkov.ui.help

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.bmimolotkov.R
import com.example.bmimolotkov.databinding.FragmentHelpBinding
import com.example.bmimolotkov.database.Prefs
import com.google.gson.Gson

class HelpFragment : Fragment(R.layout.fragment_help) {

    lateinit var binding: FragmentHelpBinding

    private lateinit var viewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this, HelpViewModelFactory(createPrefs()))
            .get(HelpViewModel::class.java)

        binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                Navigation.findNavController(view).navigate(R.id.action_helpFragment_to_mainFragment)
            } else {
                Navigation.findNavController(view).navigate(R.id.action_helpFragment_to_startFragment)
            }
        })
    }

    private fun createPrefs() = Prefs(
        requireContext().getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE),
        Gson()
    )
}
