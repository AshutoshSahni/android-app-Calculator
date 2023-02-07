package com.ashutoshsahni.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
//import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput : TextView? = null
    private var isLastDigit : Boolean = false
    private  var isLastDecimal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        isLastDigit = true
        isLastDecimal = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view : View) {
        if(!isLastDecimal && isLastDigit) {
            tvInput?.append(".")
            isLastDecimal = true
            isLastDigit = false
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if(isLastDigit && (!isOperatorAdded(it.toString()))) {
                tvInput?.append((view as Button).text)
                isLastDecimal = false
                isLastDigit = false
            }
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }
    }

    fun onEqual(view: View) {
        if(isLastDigit) {
            var tvValue = tvInput?.text
            var prefix = ""
            try {
                if(tvValue?.startsWith("-") == true) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue?.contains("-") == true) {
                    val values = tvValue.split("-")
                    var operator1 = values[0]
                    val operator2 = values[1]

                    if (prefix.isNotEmpty()) {
                        operator1 = prefix + operator1
                    }
                    tvInput?.text = remoteZeroAfterDot((operator1.toDouble() - operator2.toDouble()).toString())
                } else if(tvValue?.contains("+") == true) {
                    val values = tvValue.split("+")
                    var operator1 = values[0]
                    val operator2 = values[1]

                    if (prefix.isNotEmpty()) {
                        operator1 = prefix + operator1
                    }
                    tvInput?.text = remoteZeroAfterDot((operator1.toDouble() + operator2.toDouble()).toString())
                } else if(tvValue?.contains("*") == true) {
                    val values = tvValue.split("*")
                    var operator1 = values[0]
                    val operator2 = values[1]

                    if (prefix.isNotEmpty()) {
                        operator1 = prefix + operator1
                    }
                    tvInput?.text = remoteZeroAfterDot((operator1.toDouble() * operator2.toDouble()).toString())
                } else if(tvValue?.contains("/") == true) {
                    val values = tvValue.split("/")
                    var operator1 = values[0]
                    val operator2 = values[1]

                    if (prefix.isNotEmpty()) {
                        operator1 = prefix + operator1
                    }
                    tvInput?.text = remoteZeroAfterDot((operator1.toDouble() / operator2.toDouble()).toString())
                }
            } catch (e: java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun remoteZeroAfterDot(value:String): String {
        var result = value
        if (result.contains(".0"))
            result = result.substring(0, result.length - 2)

        return result
    }
}