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
        binding.button.setOnClickListener(submitOrder(binding));
        binding.price.text = NumberFormat.getCurrencyInstance().format(0.00);

    }

    fun submitOrder(binding: ActivityMainBinding): View.OnClickListener? {
        return View.OnClickListener {
            binding.quantityEditText.text = "5";
            displayPrice(binding);
        };
    };

    fun displayPrice(binding: ActivityMainBinding) {
        binding.price.text = NumberFormat.getCurrencyInstance()
            .format(Integer.parseInt(binding.quantityEditText.text.toString()));
    }
}



