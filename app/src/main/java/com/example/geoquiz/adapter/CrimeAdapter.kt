package com.example.geoquiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geoquiz.Crime
import com.example.geoquiz.databinding.ItemCrimeBinding
import com.example.geoquiz.databinding.ItemCrimePoliceBinding

private const val CRIME_VIEW_TYPE = 0
private const val POLICE_VIEW_TYPE = 1

class CrimeAdapter(private var crimes: List<Crime>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int =
        if (crimes[position].isSolved) {
            POLICE_VIEW_TYPE
        } else {
            CRIME_VIEW_TYPE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CRIME_VIEW_TYPE -> CrimeHolder(ItemCrimeBinding.inflate(inflater, parent, false))
            POLICE_VIEW_TYPE -> CrimePoliceHolder(
                ItemCrimePoliceBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Wrong viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        when (holder) {
            is CrimeHolder -> holder.bind(crime)
            is CrimePoliceHolder -> holder.bind(crime)
        }
    }

    override fun getItemCount() = crimes.size
}