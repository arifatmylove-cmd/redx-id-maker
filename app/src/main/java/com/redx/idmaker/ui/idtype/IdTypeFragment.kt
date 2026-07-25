package com.redx.idmaker.ui.idtype

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
import com.redx.idmaker.databinding.FragmentIdTypeBinding

class IdTypeFragment : Fragment() {
    private var _binding: FragmentIdTypeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIdTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val country = viewModel.selectedCountry.value ?: return
        binding.tvCountryHeader.text = "${country.flag} ${country.name}"
        val adapter = IdTypeAdapter(country.idTypes) { idType ->
            viewModel.selectedIdType.value = idType
            findNavController().navigate(R.id.action_idtype_to_form)
        }
        binding.rvIdTypes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIdTypes.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
