package com.redx.idmaker.ui.idtype

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redx.idmaker.data.IdType
import com.redx.idmaker.databinding.ItemIdTypeBinding

class IdTypeAdapter(
    private val idTypes: List<IdType>,
    private val onSelect: (IdType) -> Unit
) : RecyclerView.Adapter<IdTypeAdapter.VH>() {

    inner class VH(val binding: ItemIdTypeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemIdTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = idTypes.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val idType = idTypes[position]
        holder.binding.tvIdTypeName.text = idType.name
        holder.binding.tvCategory.text = idType.category.name.replace("_", " ")
        holder.binding.root.setOnClickListener { onSelect(idType) }
    }
}
