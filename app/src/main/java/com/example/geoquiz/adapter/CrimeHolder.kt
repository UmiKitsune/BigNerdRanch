package com.example.geoquiz.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.geoquiz.Crime
import com.example.geoquiz.ui.CrimeListFragment
import com.example.geoquiz.databinding.ItemCrimeBinding

class CrimeHolder(
    binding: ItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var lateCrime: Crime
    private val title = binding.crimeTitle
    private val date = binding.crimeDate

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(crime: Crime) {
        this.lateCrime = crime
        title.text = this.lateCrime.title
        date.text = this.lateCrime.date.toString()
    }

    override fun onClick(view: View?) {
        CrimeListFragment.callbacks?.onCrimeSelected(lateCrime.id)
    }
}