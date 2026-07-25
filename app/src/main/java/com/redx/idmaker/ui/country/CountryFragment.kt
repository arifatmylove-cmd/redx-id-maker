package com.redx.idmaker.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redx.idmaker.R
import com.redx.idmaker.SharedViewModel
import com.redx.idmaker.data.CountryData
import com.redx.idmaker.databinding.FragmentCountryBinding

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CountryAdapter(CountryData.countries) { country ->
            viewModel.selectedCountry.value = country
            findNavController().navigate(R.id.action_country_to_idtype)
        }
        binding.rvCountries.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCountries.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
