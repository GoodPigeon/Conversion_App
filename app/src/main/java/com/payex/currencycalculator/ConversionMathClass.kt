package com.payex.currencycalculator

class ConversionMathClass {

    //Function for calculations in the conversion
    fun convert(currencyFrom: String, currencyTo: String, amount: Double, currencyToday: Double): Double{
        return when{
            currencyFrom == "USD" && currencyTo =="NOK"-> (amount*currencyToday)
            currencyFrom == "NOK" && currencyTo =="USD"-> (amount/currencyToday)
            else -> (amount) //if currenctFrom equals currencyTo
        }
    }
}