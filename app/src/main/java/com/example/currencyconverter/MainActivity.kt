package com.example.currencyconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverter.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val currencies = listOf("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "INR")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupConvertButton()
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter
    }

    private fun setupConvertButton() {
        binding.btnConvert.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            val fromCurrency = binding.spinnerFrom.selectedItem.toString()
            val toCurrency = binding.spinnerTo.selectedItem.toString()

            if (amount.isNotEmpty()) {
                convertCurrency(amount, fromCurrency, toCurrency)
            } else {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertCurrency(amount: String, from: String, to: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.convertCurrency(
                    apiKey = "9b5d3211d41083e1dc6f5a71f1057f8dd4888be6",
                    from = from,
                    to = to,
                    amount = amount
                )
                val convertedAmount = response.rates[to]
                binding.tvResult.text = "Converted amount: $amount $from = $convertedAmount $to"
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}