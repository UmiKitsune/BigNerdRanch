package com.example.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0


    private val questionBank = listOf(
        Question(R.string.q_australia, true),
        Question(R.string.q_oceans, true),
        Question(R.string.q_mideast, false),
        Question(R.string.q_africa, false),
        Question(R.string.q_americas, true),
        Question(R.string.q_asia, true),
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val bankSize: Int
        get() = questionBank.size

    fun moveToNext() {
        currentIndex += 1
    }

}