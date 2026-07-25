package com.redx.idmaker.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redx.idmaker.data.Country
import com.redx.idmaker.databinding.ItemCountryBinding

class CountryAdapter(
    private val countries: List<Country>,
    private val onSelect: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.VH>() {

    inner class VH(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val country = countries[position]
        holder.binding.tvFlag.text = country.flag
        holder.binding.tvName.text = country.name
        holder.binding.root.setOnClickListener { onSelect(country) }
    }
}
