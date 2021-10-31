package com.example.kodelabfirstapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val args: FirstFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.random_button).setOnClickListener {
            val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
            val currentCount = showCountTextView.text.toString().toInt()
            val tentatives = view.findViewById<TextView>(R.id.textView_tentativas).text.toString()
                .filter { it.isDigit() }.toInt()
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                currentCount,
                tentatives
            )
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.toast_button).setOnClickListener {
            val myToast = Toast.makeText(context, "Hello World", Toast.LENGTH_LONG)
            myToast.show()
        }

        view.findViewById<Button>(R.id.count_button).setOnClickListener {
            countMe(view)
        }

        displayTentatives(view)
    }

    private fun displayTentatives(view: View) {
        if (args.randomDaSecond.isNotEmpty()) {
            view.findViewById<TextView>(R.id.textview_first).text = args.randomDaSecond
            view.findViewById<TextView>(R.id.textView_tentativas).text =
                getString(R.string.text_tentativas, args.tentativaFirst.toString().toInt())
            if (args.randomDaSecond.toString().toInt() > 50) {
                TestDone(view)
            }
        } else {
            view.findViewById<TextView>(R.id.textView_tentativas).text =
                getString(R.string.text_tentativas, 0)
        }
    }

    private fun TestDone(view: View) {
        view.findViewById<Button>(R.id.random_button).isEnabled = false;
        view.findViewById<Button>(R.id.count_button).isEnabled = false;
        val myToast = Toast.makeText(context, "Teste concluído !", Toast.LENGTH_LONG)
        myToast.show()
    }

    private fun countMe(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        showCountTextView.text = showCountTextView.text.toString().toInt().inc().toString()
    }

}