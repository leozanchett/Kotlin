package com.example.justjava

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.justjava.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        binding.btnplus.setOnClickListener(increment(binding));
        binding.btnminus.setOnClickListener(decrement(binding));
        binding.price.text = NumberFormat.getCurrencyInstance().format(5.00);

    }

    private fun increment(binding: ActivityMainBinding): View.OnClickListener? {
        return View.OnClickListener {
            binding.quantityEditText.text =
                "${Integer.parseInt(binding.quantityEditText.text.toString()).inc()}";
            totalizador(binding);
        }
    }

    private fun decrement(binding: ActivityMainBinding): View.OnClickListener? {
        return View.OnClickListener {
            if (Integer.parseInt(binding.quantityEditText.text.toString()) > 0) {
                binding.quantityEditText.text =
                    "${Integer.parseInt(binding.quantityEditText.text.toString()).dec()}";
                totalizador(binding);
            }
        }
    }

    private fun totalizador(binding: ActivityMainBinding) {
        binding.price.text = NumberFormat.getCurrencyInstance()
            .format(5.00 * Integer.parseInt(binding.quantityEditText.text.toString()));
    }

}



