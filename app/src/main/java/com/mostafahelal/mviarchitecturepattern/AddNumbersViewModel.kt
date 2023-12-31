package com.mostafahelal.mviarchitecturepattern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumbersViewModel : ViewModel() {
    val channels=Channel<MainIntent>(Channel.UNLIMITED)
    //not fire data when duplicates
    private val _viewState=MutableStateFlow<MainViewState>(MainViewState.idle)
    val state:StateFlow<MainViewState> =_viewState
    private var number=0
    //proccess
    init {
        processIntent()
    }
    private fun processIntent(){
        viewModelScope.launch {
            channels.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.addNumberIntent -> addNumber()
                }
            }
        }

    }
    //Reduce
    private fun addNumber(){
        viewModelScope.launch {
            _viewState.value=
                try {
                    MainViewState.number(number++)

                }catch (e: Exception) {
                    MainViewState.error(e.message!!)

                }
        }
    }


}