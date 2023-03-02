package com.example.geoquiz.ui

import androidx.lifecycle.ViewModel
import com.example.geoquiz.Crime

class CrimeListViewModel: ViewModel() {

//    private val crimeRepository =CrimeRepository.get()
//    val crimeListLiveData = crimeRepository.getCrimes()

    val crimes = mutableListOf<Crime>()

    init {
        for (i in 1 .. 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
        }
    }
}