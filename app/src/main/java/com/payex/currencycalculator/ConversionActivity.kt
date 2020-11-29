package com.payex.currencycalculator
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class ConversionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        VALUES AND CLASS INSTANCES
        */
        val connection = Internet()
        val database = Database()
        val test = Test()
        val conversionMath = ConversionMath()
        val runTests = true //if true, calls function for running tests on functions

        /*
        DESIGN ELEMENTS
        */
        val currencyFromEl = findViewById<Spinner>(R.id.currencyFrom)
        val currencyToEl = findViewById<Spinner>(R.id.currencyTo)
        val inputEl = findViewById<EditText>(R.id.numberInput)
        val resultsEl = findViewById<EditText>(R.id.results)
        val convertButtonEl = findViewById<Button>(R.id.convertButton)

        /*
        FUNCTION
        */

        //function that retrieves input values, calls conversion function and updates results
        fun conversion(){
            val currencyToString = currencyToEl.selectedItem.toString()
            val currencyFromString = currencyFromEl.selectedItem.toString()
            if(inputEl.text.toString()==""){
                inputEl.setText("0")
            }
            val inputDouble = inputEl.text.toString().toDouble()
            val resultInt = conversionMath.convert(currencyFromString, currencyToString, inputDouble, database.currencyToday)
            val df = DecimalFormat("#.#")
            df.maximumFractionDigits = 4
            val resultString = getString(R.string.resultString, df.format(inputDouble).toString(), currencyFromString, df.format(resultInt).toString(),currencyToString, database.date)
            resultsEl.setText(resultString)
        }

        /*
        FLOW
        */

        //First internet check
        if(connection.isOnline(this)){
            database.connectionThread.start()
        }
        else {
            Toast.makeText(this, "Try connecting to the internet", Toast.LENGTH_LONG).show()
        }

        //At button press, conversion process begins. Button also requires the app to be connected to database
        convertButtonEl.setOnClickListener{
            if(database.readyToConvert) {
                conversion()
            }
        }

        if(runTests){
            val testResults = test.runTestFunction()
            Log.d("Test", testResults)
            Toast.makeText(this, testResults, Toast.LENGTH_LONG).show()
        }
    }
}