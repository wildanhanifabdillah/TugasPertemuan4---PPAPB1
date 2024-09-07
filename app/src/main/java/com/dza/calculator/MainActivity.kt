package com.dza.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dza.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Inisialisasi ViewBinding
    private lateinit var binding: ActivityMainBinding

    private var operand1: String = ""
    private var operand2: String = ""
    private var operator: String = ""
    private var result: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur padding untuk sistem bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set click listeners for buttons
        binding.apply {
            btn0.setOnClickListener { appendDigit("0") }
            btn1.setOnClickListener { appendDigit("1") }
            btn2.setOnClickListener { appendDigit("2") }
            btn3.setOnClickListener { appendDigit("3") }
            btn4.setOnClickListener { appendDigit("4") }
            btn5.setOnClickListener { appendDigit("5") }
            btn6.setOnClickListener { appendDigit("6") }
            btn7.setOnClickListener { appendDigit("7") }
            btn8.setOnClickListener { appendDigit("8") }
            btn9.setOnClickListener { appendDigit("9") }

            btnPlus.setOnClickListener { setOperator("+") }
            btnMinus.setOnClickListener { setOperator("-") }
            btnMultiply.setOnClickListener { setOperator("x") }
            btnDivide.setOnClickListener { setOperator("/") }
            btnPercent.setOnClickListener { applyPercentage() }

            btnAC.setOnClickListener { clear() }
            btnEquals.setOnClickListener { calculateResult() }
            btnDot.setOnClickListener { appendDot() }
        }
    }

    // Append digits to operand
    private fun appendDigit(digit: String) {
        if (operator.isEmpty()) {
            operand1 += digit
            binding.txtOperation.text = operand1
        } else {
            operand2 += digit
            binding.txtOperation.text = "$operand1 $operator $operand2"
        }
    }

    // Set the operator (+, -, *, /)
    private fun setOperator(op: String) {
        if (operand1.isNotEmpty()) {
            operator = op
            binding.txtOperation.text = "$operand1 $operator"
        }
    }

    // Append a decimal dot
    private fun appendDot() {
        if (operator.isEmpty() && !operand1.contains(".")) {
            operand1 += "."
            binding.txtOperation.text = operand1
        } else if (operator.isNotEmpty() && !operand2.contains(".")) {
            operand2 += "."
            binding.txtOperation.text = "$operand1 $operator $operand2"
        }
    }

    // Clear all operands and operator
    private fun clear() {
        operand1 = ""
        operand2 = ""
        operator = ""
        result = ""
        binding.txtOperation.text = "0"
        binding.txtResult.text = ""
    }

    // Calculate percentage or show error
    private fun applyPercentage() {
        if (operand1.isNotEmpty() && operator.isEmpty()) {
            // Mengalikan operand1 dengan 0.01
            operand1 = (operand1.toDouble() * 0.01).toString()
            binding.txtOperation.text = operand1
        } else {
            // Jika operator sudah ada, tidak valid untuk menggunakan % pada operand kedua
            binding.txtOperation.text = "Error"
        }
    }

    // Perform calculation
    private fun calculateResult() {
        if (operand1.isNotEmpty() && operand2.isNotEmpty()) {
            val num1 = operand1.toDouble()
            val num2 = operand2.toDouble()
            result = when (operator) {
                "+" -> (num1 + num2).toString()
                "-" -> (num1 - num2).toString()
                "x" -> (num1 * num2).toString()
                "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
                else -> ""
            }
            binding.txtResult.text = result
        }
    }
}
