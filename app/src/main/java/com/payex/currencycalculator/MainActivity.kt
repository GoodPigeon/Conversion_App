package com.payex.currencycalculator

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.jsoup.Jsoup
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        VALUES
        */
        val databaseURL = "https://data.norges-bank.no/api/data/EXR/B.USD.NOK.SP?format=sdmx-generic-2.1&amp;lastNObservations=1&amp;locale=en"
        var currencyToday = 0.0//is automatically updated by database

        /*
        DESIGN ELEMENTS
        */
        val currencyFromEl = findViewById<Spinner>(R.id.currencyFrom)
        val currencyToEl = findViewById<Spinner>(R.id.currencyTo)
        val inputEl = findViewById<EditText>(R.id.numberInput)
        val resultsEl = findViewById<EditText>(R.id.results)
        val convertButtonEl = findViewById<Button>(R.id.convertButton)

        /*
         FUNCTIONS
        */

        //Function for checking whether internet connection persists
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        //Function for calculations in the conversion
        fun convertionMath(currencyFrom: String, currencyTo: String, amount: Double, currencyToday: Double): Double{
            var result = 0.0
            if(currencyFrom.equals("NOK")){
                //NOK to NOK
                if(currencyTo.equals("NOK")){
                    result = amount
                }
                //NOK to USD
                else{
                    result =  (amount/currencyToday)
                }
            }
            if(currencyFrom.equals("USD")){
                //USD to USD
                if(currencyTo.equals("USD")){
                    result = amount
                }
                //USD to NOK
                else{
                    result = (amount * currencyToday)
                }
            }
            return result
        }

        //function that retrieves input values, calls conversion method and updates results
        fun conversion(){
            val currencyToString = currencyToEl.selectedItem.toString()
            val currencyFromString = currencyFromEl.selectedItem.toString()
            if(inputEl.text.toString()==""){
                inputEl.setText("0")
            }
            val inputInt = inputEl.text.toString().toDouble()
            val resultInt = convertionMath(
                currencyFromString,
                currencyToString,
                inputInt,
                currencyToday
            )

            val df = DecimalFormat("#.#")
            df.maximumFractionDigits = 10
            resultsEl.setText(df.format(resultInt).toString())
        }

        //Thread that connects to database
        val connectionThread = Thread {
            try {
                val doc = Jsoup.connect(databaseURL).get()
                val content = doc.getElementsByTag("generic:ObsValue")
                val retrievedCurrency = content.last().attr("value")
                currencyToday = retrievedCurrency.toDouble()
            } catch (e: Exception) {
                Log.d("TEST1", "Something went wrong: $e")
            }
        }

        /*
        FLOW
        */

        if(isOnline(this)){
            Log.d("TEST1", currencyToday.toString())
            connectionThread.start()
            Log.d("TEST1", currencyToday.toString())
            convertButtonEl.isEnabled = true
            convertButtonEl.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        }
        else {
            Toast.makeText(this, "Try connecting to the internet", Toast.LENGTH_LONG).show()
        }

        //At button press, conversion process happens
        convertButtonEl.setOnClickListener{
            conversion()
        }
    }
}