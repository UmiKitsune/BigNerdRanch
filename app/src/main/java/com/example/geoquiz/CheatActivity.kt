package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.geoquiz.databinding.ActivityCheatBinding
import com.example.geoquiz.viewmodels.CheatViewModel
import com.google.android.material.tabs.TabLayout
import kotlin.properties.Delegates

private const val TAG = "tag for cheat"
private const val EXTRA_ANSWER_IS_TRUE = "answer is true"
const val EXTRA_ANSWER_SHOWN = "answer shown"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    private var cheatFlag by Delegates.notNull<Boolean>()

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this)[CheatViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater).also { setContentView(it.root) }

        cheatFlag = cheatViewModel.cheatingFlag

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener {
            val answerText = when(answerIsTrue) {
                true -> R.string.main_btn_true
                else -> R.string.main_btn_false
            }
            cheatViewModel.clickOnCheatButton()
            cheatFlag = cheatViewModel.cheatingFlag
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(cheatFlag)
        }
        setAnswerShownResult(cheatFlag)

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerTrue: Boolean): Intent =
            Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerTrue)
            }
    }
}