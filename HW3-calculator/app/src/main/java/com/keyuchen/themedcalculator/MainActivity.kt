package com.keyuchen.themedcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var currentOperator: String? = null
    private var isNewOperation = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val num1 = findViewById<Button>(R.id.num1)
        val num2 = findViewById<Button>(R.id.num2)
        val num3 = findViewById<Button>(R.id.num3)
        val num4 = findViewById<Button>(R.id.num4)
        val num5 = findViewById<Button>(R.id.num5)
        val num6 = findViewById<Button>(R.id.num6)
        val num7 = findViewById<Button>(R.id.num7)
        val num8 = findViewById<Button>(R.id.num8)
        val num9 = findViewById<Button>(R.id.num9)
        val num0 = findViewById<Button>(R.id.num0)

        val plus = findViewById<Button>(R.id.Plus)
        val minus = findViewById<Button>(R.id.Minus)
        val multiply = findViewById<Button>(R.id.Multiply)
        val divide = findViewById<Button>(R.id.Divide)
        val equal = findViewById<Button>(R.id.Equal)
        val delete = findViewById<Button>(R.id.Delete)


        //bonus:
        val point = findViewById<Button>(R.id.Point)
        val negative = findViewById<Button>(R.id.negative)

        num1.setOnClickListener { numberClick("1") }
        num2.setOnClickListener { numberClick("2") }
        num3.setOnClickListener { numberClick("3") }
        num4.setOnClickListener { numberClick("4") }
        num5.setOnClickListener { numberClick("5") }
        num6.setOnClickListener { numberClick("6") }
        num7.setOnClickListener { numberClick("7") }
        num8.setOnClickListener { numberClick("8") }
        num9.setOnClickListener { numberClick("9") }
        num0.setOnClickListener { numberClick("0") }

        plus.setOnClickListener { operatorClick("+") }
        minus.setOnClickListener { operatorClick("-") }
        multiply.setOnClickListener { operatorClick("*") }
        divide.setOnClickListener { operatorClick("/") }
        point.setOnClickListener {pointAdd()}
        negative.setOnClickListener{changeToNegative()}
        equal.setOnClickListener { getResult() }
        delete.setOnClickListener { clearDisplay() }
    }

    private fun numberClick(number: String) {
        if (isNewOperation) {
            display.text = number
            isNewOperation = false
        } else {
            display.append(number)
        }
    }

    private fun operatorClick(operator: String) {
        currentOperator = operator
        operand1 = display.text.toString().toDouble()
        isNewOperation = true
    }

    // bonus
    private fun pointAdd(){
        if (!display.text.contains(".")) {
            display.append(".")
        }
    }

    private fun changeToNegative() {
        val currentText = display.text.toString()

        if (currentText.isNotEmpty()) {
            if (!currentText.startsWith("-")) {
                display.text = "-$currentText"
            } else {
                display.text = currentText.removePrefix("-")
            }
        }
    }


    private fun getResult(){
        operand2 = display.text.toString().toDouble()
        val result = when (currentOperator){
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> if(operand2 != 0.0) operand1 / operand2 else "errors"
            else -> 0.0
        }
        display.text = result.toString()
        isNewOperation = true
    }

    private fun clearDisplay() {
        display.text = "0"
        operand1 = 0.0
        operand2 = 0.0
        currentOperator = null
        isNewOperation = true
    }
}
