package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.geoquiz.databinding.ActivityCheatBinding
import com.example.geoquiz.viewmodels.CheatViewModel
import kotlin.properties.Delegates

private const val EXTRA_ANSWER_IS_TRUE = "answer is true"
private const val EXTRA_GET_CHEAT_COUNT = "get extra count"
const val EXTRA_CHEAT_COUNT = "cheat count"
const val EXTRA_ANSWER_SHOWN = "answer shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    private var cheatFlag by Delegates.notNull<Boolean>()
    private var cheatCount by Delegates.notNull<Int>()
    private var getCheatCount by Delegates.notNull<Int>()

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this)[CheatViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater).also { setContentView(it.root) }

        cheatFlag = cheatViewModel.cheatingFlag
        cheatCount = cheatViewModel.cheatCount

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        getCheatCount = intent.getIntExtra(EXTRA_GET_CHEAT_COUNT, 0)
        cheatViewModel.cheatCount = getCheatCount

        if (getCheatCount == 0) binding.showAnswerButton.isEnabled = false

        binding.showAnswerButton.setOnClickListener {
            val answerText = when (answerIsTrue) {
                true -> R.string.main_btn_true
                else -> R.string.main_btn_false
            }
            cheatViewModel.clickOnCheatButton()
            cheatFlag = cheatViewModel.cheatingFlag
            cheatCount = cheatViewModel.cheatCount
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(cheatFlag, getCheatCount)
        }
        setAnswerShownResult(cheatFlag, getCheatCount)

        binding.showApiLvl.text = "API Level ${Build.VERSION.SDK_INT}"
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean, cheatCount: Int) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
            putExtra(EXTRA_CHEAT_COUNT, cheatCount)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerTrue: Boolean, cheatCount: Int): Intent =
            Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerTrue)
                putExtra(EXTRA_GET_CHEAT_COUNT, cheatCount)
            }
    }
}