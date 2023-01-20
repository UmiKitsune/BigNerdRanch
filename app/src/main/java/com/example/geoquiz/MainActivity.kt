package com.example.geoquiz

import android.os.Bundle
import android.view.Gravity.TOP
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var nextButton: ImageButton

    private val questionBank = listOf(
        Question(R.string.q_australia, true),
        Question(R.string.q_oceans, true),
        Question(R.string.q_mideast, false),
        Question(R.string.q_africa, false),
        Question(R.string.q_americas, true),
        Question(R.string.q_asia, true),
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener { view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()

    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId =
            if (userAnswer == correctAnswer)  R.string.correct_toast else R.string.incorrect_toast
        toastTop(messageResId)
    }

    private fun toastTop(textRes: Int) {
        val toast = Toast.makeText(this, textRes, Toast.LENGTH_SHORT)
        with(toast) {
            setGravity(TOP, 0, 250)
            show()
        }
    }

}