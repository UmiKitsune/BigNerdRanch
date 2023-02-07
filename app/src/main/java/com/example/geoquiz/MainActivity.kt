package com.example.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.Gravity.TOP
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.viewmodels.QuizViewModel
import kotlin.properties.Delegates

private const val KEI_INDEX = "KeyIndex"
private const val KEI_INDEX_FLAG = "KeyIndexFlag"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var rightAnswers = 0
    private var answeredFlag = false
    private var cheatCount = 3

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater = it.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            cheatCount = it.data?.getIntExtra(EXTRA_CHEAT_COUNT, 0) ?: 0
        }
    }


    @SuppressLint("RestrictedApi")//для проверки уровня sdk в кнопке cheatButton
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

        binding.cheatButton.setOnClickListener { view ->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue, cheatCount)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //проверка на подходящий уровень sdk для анимации (если меньше 23)
                val options = ActivityOptionsCompat.makeClipRevealAnimation(view, 0, 0, view.width, view.height)
                getResult.launch(intent, options)
            } else {
                getResult.launch(intent)
            }
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