package com.example.geoquiz.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geoquiz.adapter.CrimeAdapter
import com.example.geoquiz.databinding.FragmentCrimeListBinding
import java.util.*

class CrimeListFragment : Fragment() {

    interface CallBacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private lateinit var binding: FragmentCrimeListBinding
    private val crimeListViewModel: CrimeListViewModel by viewModels()
    //private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())
    private var adapter: CrimeAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as CallBacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        binding.crimeRecycleView.layoutManager = LinearLayoutManager(context)
        //binding.crimeRecycleView.adapter = adapter
        updateUI()
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        crimeListViewModel.crimeListLiveData.observe(
//            viewLifecycleOwner,
//             { crimes ->
//                crimes?.let {
//                    updateUI(crimes)
//                }
//            })
//    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI() { //crimes: List<Crime>
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        binding.crimeRecycleView.adapter = adapter
    }

    companion object {
        var callbacks: CallBacks? = null
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}