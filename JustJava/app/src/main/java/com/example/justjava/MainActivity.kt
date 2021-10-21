package com.example.justjava

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.justjava.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        binding.button.setOnClickListener(submitOrder(binding));

    }

    fun submitOrder(binding: ActivityMainBinding): View.OnClickListener? {
        return View.OnClickListener {
            binding.quantityEditText.text = "5";
        };
    };



}



