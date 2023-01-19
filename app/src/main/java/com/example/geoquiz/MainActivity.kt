package com.example.geoquiz

import android.os.Bundle
import android.view.Gravity.TOP
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view: View ->
            toastTop(R.string.correct_toast)
        }

        falseButton.setOnClickListener { view: View ->
            toastTop(R.string.incorrect_toast)

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