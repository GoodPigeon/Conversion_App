package com.payex.currencycalculator
import android.util.Log
import org.jsoup.Jsoup

class DatabaseClass() {
    val databaseURL = "https://data.norges-bank.no/api/data/EXR/B.USD.NOK.SP?format=sdmx-generic-2.1&amp;lastNObservations=1&amp;locale=en"
    var currencyToday = 0.0 //will be automatically updated by database
    var date = "" //will be automatically updated by database
    var readyToConvert = false //will be set to true if

    //Thread that connects to database
    val connectionThread = Thread {
        try {
            val doc = Jsoup.connect(databaseURL).get()
            //retrieves currency
            val currencyContent = doc.getElementsByTag("generic:ObsValue")
            val retrievedCurrency = currencyContent.last().attr("value")
            currencyToday = retrievedCurrency.toDouble()

            //retrieves date of last update to database (No weekend updates!)
            val dateContent = doc.getElementsByTag("generic:ObsDimension")
            val retrievedDate = dateContent.last().attr("value")
            date = retrievedDate

            // is ok for conversion
            readyToConvert = true
            Log.d("DatabaseClass", "Successful connection")


        } catch (e: Exception) {
            Log.d("DatabaseClass", "Something went wrong: $e")
        }
    }
}