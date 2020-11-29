package com.payex.currencycalculator

class TestClass {
    //tests conversion function (only ran if value of runTests == true)
    fun runTestFunction() : String{
        val convMath = ConversionMathClass ()

        //checks conversionMath function with different params
        val test1 = convMath.convert("NOK", "USD", 100.0, 10.0)
        val test2 = convMath.convert("USD", "NOK", 0.0, 0.0)
        val test3 = convMath.convert("USD", "USD", 100.0, 10.0)
        val test4 = convMath.convert("NOK", "NOK", 0.0, 0.0)
        return when{
            test1 != 10.0   -> "Error in test 1!"
            test2 != 0.0    -> "Error in test 2!"
            test3 != 100.0 -> "Error in test 3!"
            test4 != 0.0    -> "Error in test 4!"
            else -> "All tests were successful!"
        }
    }
}