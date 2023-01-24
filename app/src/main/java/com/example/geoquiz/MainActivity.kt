package com.example.geoquiz

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity.TOP
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEI_INDEX = "KeyIndex"
private const val KEI_INDEX_FLAG = "KeyIndexFlag"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var percentTV: TextView
    private lateinit var nextButton: ImageButton
    private lateinit var questionLayout: LinearLayout
    private lateinit var rightAnswersLayout: LinearLayout

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private var rightAnswers = 0
    private var answeredFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rightAnswers = savedInstanceState?.getInt(KEI_INDEX, 0) ?: 0
        answeredFlag = savedInstanceState?.getBoolean(KEI_INDEX_FLAG, false) ?: false

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionLayout = findViewById(R.id.question_layout)
        rightAnswersLayout = findViewById(R.id.right_answers_layout)
        percentTV = findViewById(R.id.percent)

        updateQuestion()

        trueButton.setOnClickListener { view: View ->
            answeredFlag = true
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            answeredFlag = true
            checkAnswer(false)
        }

        nextButton.setOnClickListener { view: View ->
            answeredFlag = false
            //currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            answeredFlag = false
            quizViewModel.moveToNext()
            updateQuestion()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEI_INDEX, rightAnswers)
        outState.putBoolean(KEI_INDEX_FLAG, answeredFlag)
    }

    private fun updateQuestion() {
        if (quizViewModel.currentIndex < quizViewModel.bankSize) {
            val questionTextResId = quizViewModel.currentQuestionText
            questionTextView.setText(questionTextResId)
            if (answeredFlag) {
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            } else {
                trueButton.isEnabled = true
                falseButton.isEnabled = true
            }
        }  else {
            questionLayout.visibility = View.GONE
            rightAnswersLayout.visibility = View.VISIBLE
            val percent = ((rightAnswers / quizViewModel.bankSize) * 100).toInt()
            percentTV.text = resources.getString(R.string.percent_text, rightAnswers, quizViewModel.bankSize)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
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