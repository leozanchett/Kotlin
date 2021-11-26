package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.roll_button)
        val countUpButton: Button = findViewById(R.id.count_up)
        resultText = findViewById(R.id.result_text)
        rollButton.setOnClickListener {
            rollDice()
        }
        countUpButton.setOnClickListener {
            countUp()
        }
    }

    private fun rollDice() {
        val randomInt = (1..6).random()
        resultText.text = randomInt.toString()
    }

    private fun countUp() {
        if ((resultText.text.isDigitsOnly()) and (resultText.text.toString() != "6")) {
            resultText.text = (resultText.text.toString().toInt() + 1).toString()
        }
    }
}