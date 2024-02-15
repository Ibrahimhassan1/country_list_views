package com.softups.countrylist.ui.country_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softups.countrylist.databinding.CountryRowItemBinding
import com.softups.countrylist.domain.model.Country

class CountryListRecyclerViewAdapter(private val dataSet: List<Country>) :
    RecyclerView.Adapter<CountryListRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CountryRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CountryRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            dataSet[position].apply {
                binding.nameTextView.text = "$name, $region"
                binding.codeTextView.text = code
                binding.capitalTextView.text = capital
            }
        }
    }
}