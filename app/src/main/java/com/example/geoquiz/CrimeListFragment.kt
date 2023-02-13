package com.example.geoquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geoquiz.databinding.FragmentCrimeListBinding
import com.example.geoquiz.databinding.ItemCrimeBinding
import com.example.geoquiz.databinding.ItemCrimePoliceBinding

class CrimeListFragment : Fragment() {
    private lateinit var binding: FragmentCrimeListBinding
    private val crimeListViewModel: CrimeListViewModel by viewModels()
    private var adapter: CrimeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        binding.crimeRecycleView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return binding.root
    }

    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        binding.crimeRecycleView.adapter = adapter
    }

    companion object {
        const val CRIME_VIEW_TYPE = 0
        const val POLICE_VIEW_TYPE = 1
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeHolder(
        binding: ItemCrimeBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var lateCrime: Crime
        val title = binding.crimeTitle
        val date = binding.crimeDate

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.lateCrime = crime
            title.text = this.lateCrime.title
            date.text = this.lateCrime.date.toString()
        }

        override fun onClick(view: View?) {
            Toast.makeText(context, "${lateCrime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimePoliceHolder(
        binding: ItemCrimePoliceBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var lateCrime: Crime
        val title = binding.crimeTitle
        val date = binding.crimeDate

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.lateCrime = crime
            title.text = this.lateCrime.title
            date.text = this.lateCrime.date.toString()
        }

        override fun onClick(view: View?) {
            Toast.makeText(context, "${lateCrime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int =
            if (crimes[position].isSolved) {
                POLICE_VIEW_TYPE
            } else {
                CRIME_VIEW_TYPE
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when(viewType) {
                CRIME_VIEW_TYPE -> CrimeHolder(ItemCrimeBinding.inflate(inflater, parent, false))
                POLICE_VIEW_TYPE -> CrimePoliceHolder(ItemCrimePoliceBinding.inflate(inflater, parent, false))
                else -> throw IllegalArgumentException("Wrong viewType: $viewType")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val crime = crimes[position]
            when(holder) {
                is CrimeHolder -> holder.bind(crime)
                is CrimePoliceHolder -> holder.bind(crime)
            }
        }

        override fun getItemCount() = crimes.size
    }
}