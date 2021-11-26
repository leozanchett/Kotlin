package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    private lateinit var diceImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.roll_button)
        val countUpButton: Button = findViewById(R.id.count_up)
        diceImage = findViewById(R.id.dice_image)
        rollButton.setOnClickListener {
            rollDice()
        }
        countUpButton.setOnClickListener {
            countUp()
        }
    }

    private fun rollDice() {
        diceImage.setImageResource(getImageDice(false))
    }

    private fun getImageDice(countUp: Boolean): Int {
        val drawableResource: Int
        val random: Int
        if (countUp) {
            random = diceImage.getTag().toString().toInt() + 1
            diceImage.setTag(random)
        } else {
            random = (1..6).random()
        }
        when (random) {
            1 -> {
                drawableResource = R.drawable.dice_1
                diceImage.setTag(1)
            }
            2 -> {
                drawableResource = R.drawable.dice_2
                diceImage.setTag(2)
            }
            3 -> {
                drawableResource = R.drawable.dice_3
                diceImage.setTag(3)
            }
            4 -> {
                drawableResource = R.drawable.dice_4
                diceImage.setTag(4)
            }
            5 -> {
                drawableResource = R.drawable.dice_5
                diceImage.setTag(5)
            }
            else -> {
                drawableResource = R.drawable.dice_6
                diceImage.setTag(6)
            }
        }
        return drawableResource
    }


    private fun countUp() {
        if (diceImage.getTag() != 6) {
            diceImage.setImageResource(getImageDice(true))
        }
    }
}