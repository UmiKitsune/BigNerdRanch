package com.example.geoquiz.viewmodels

import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class CheatViewModel: ViewModel() {

    var cheatingFlag = false

    fun clickOnCheatButton() {
        cheatingFlag = true
    }
}