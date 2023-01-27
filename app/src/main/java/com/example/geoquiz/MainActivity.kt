package com.example.geoquiz

import android.app.Activity
import android.os.Bundle
import android.view.Gravity.TOP
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.viewmodels.QuizViewModel

private const val KEI_INDEX = "KeyIndex"
private const val KEI_INDEX_FLAG = "KeyIndexFlag"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater = it.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    private var rightAnswers = 0
    private var answeredFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }


        rightAnswers = savedInstanceState?.getInt(KEI_INDEX, 0) ?: 0
        answeredFlag = savedInstanceState?.getBoolean(KEI_INDEX_FLAG, false) ?: false

        updateQuestion()

        binding.trueButton.setOnClickListener {
            answeredFlag = true
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            answeredFlag = true
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            answeredFlag = false
            quizViewModel.isCheater = false
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener {
            answeredFlag = false
            quizViewModel.isCheater = false
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            getResult.launch(intent)
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
            binding.questionTextView.setText(questionTextResId)
            if (answeredFlag) {
                with(binding) {
                    trueButton.isEnabled = false
                    falseButton.isEnabled = false
                    cheatButton.isEnabled = false
                }
            } else {
                with(binding) {
                    trueButton.isEnabled = true
                    falseButton.isEnabled = true
                    cheatButton.isEnabled = true
                }
            }
        }  else {
            with(binding) {
                questionLayout.visibility = View.GONE
                rightAnswersLayout.visibility = View.VISIBLE
                percent.text = resources.getString(R.string.percent_text, rightAnswers, quizViewModel.bankSize)
            }
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId =
            when {
                quizViewModel.isCheater -> {
                    R.string.judgment_toast
                }
                userAnswer == correctAnswer -> {
                    rightAnswers += 1
                    R.string.correct_toast
                }
                else -> {
                    rightAnswers += 0
                    R.string.incorrect_toast
                }
            }
        toastTop(messageResId)
        with(binding) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            cheatButton.isEnabled = false
        }
    }

    private fun toastTop(textRes: Int) {
        val toast = Toast.makeText(this, textRes, Toast.LENGTH_SHORT)
        with(toast) {
            setGravity(TOP, 0, 250)
            show()
        }
    }

}