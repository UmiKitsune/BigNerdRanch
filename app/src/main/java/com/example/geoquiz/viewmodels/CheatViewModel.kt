package com.example.geoquiz.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

class CheatViewModel: ViewModel() {

    var cheatingFlag = false
    var cheatCount = 3

    fun clickOnCheatButton() {
        cheatingFlag = true
        if (cheatCount != 0) cheatCount -= 1
    }
}