package com.example.bmimolotkov.ui.start

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.bmimolotkov.R
import com.example.bmimolotkov.databinding.FragmentStartBinding
import com.example.bmimolotkov.database.User
import com.example.bmimolotkov.database.Prefs
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.lang.NumberFormatException

class StartFragment : Fragment(R.layout.fragment_start) {

    lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {
            var errorCount = 0

            val name = binding.nameEditText.text.toString()
            if (!nameIsValid(name)) {
                toast(getString(R.string.form_name_error))
                errorCount++
            }

            val height = try {
                binding.heightEditText.text.toString().toFloat()
            } catch (exception: NumberFormatException) {
                -1F
            }

            if (height <= 0F) {
                toast(getString(R.string.form_height_error))
                errorCount++
            }

            if (errorCount == 0) {
                createPrefs().user = User(name, height)
                Navigation.findNavController(it).navigate(R.id.action_startFragment_to_mainFragment)
            }
        }
    }

    private fun nameIsValid(name: String) =
        Regex("^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}\$").matches(name)

    private fun toast(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun createPrefs() = Prefs(
        requireContext().getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE),
        Gson()
    )
}