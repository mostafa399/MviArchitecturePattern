package com.mostafahelal.mviarchitecturepattern

sealed class MainViewState {
    //idle
    object idle : MainViewState()
    //number
    data class number(val num:Int):MainViewState()
    //error
    data class error(val err:String):MainViewState()

}