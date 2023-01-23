package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.Gravity.TOP
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var percentTV: TextView
    private lateinit var nextButton: ImageButton
    private lateinit var questionLayout: LinearLayout
    private lateinit var rightAnswersLayout: LinearLayout

    private val questionBank = listOf(
        Question(R.string.q_australia, true),
        Question(R.string.q_oceans, true),
        Question(R.string.q_mideast, false),
        Question(R.string.q_africa, false),
        Question(R.string.q_americas, true),
        Question(R.string.q_asia, true),
    )

    private var rightAnswers = 0.0
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionLayout = findViewById(R.id.question_layout)
        rightAnswersLayout = findViewById(R.id.right_answers_layout)
        percentTV = findViewById(R.id.percent)

        updateQuestion()

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener { view: View ->
            //currentIndex = (currentIndex + 1) % questionBank.size
            currentIndex += 1
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex += 1
            updateQuestion()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    private fun updateQuestion() {
        if (currentIndex < questionBank.size) {
            val questionTextResId = questionBank[currentIndex].textResId
            questionTextView.setText(questionTextResId)
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        } else {
            questionLayout.visibility = View.GONE
            rightAnswersLayout.visibility = View.VISIBLE
            val percent = ((rightAnswers / questionBank.size) * 100).toInt()
            percentTV.text = resources.getString(R.string.percent_text, percent, "%")
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId =
            if (userAnswer == correctAnswer) {
                rightAnswers += 1
                R.string.correct_toast
            } else {
                rightAnswers += 0
                R.string.incorrect_toast
            }
        toastTop(messageResId)
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }

    private fun toastTop(textRes: Int) {
        val toast = Toast.makeText(this, textRes, Toast.LENGTH_SHORT)
        with(toast) {
            setGravity(TOP, 0, 250)
            show()
        }
    }

}