package com.mostafahelal.mviarchitecturepattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.PendingIntentCompat.send
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //send() //render()
    private lateinit var numbertv:TextView
    private lateinit var addnumberbtn:Button
    private val viewModel:AddNumbersViewModel by lazy {
        ViewModelProvider(this).get(AddNumbersViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Mvi
        //Model view intent
        //activity()|| viewmodel() ||intents() ||viewstates()
        numbertv=findViewById(R.id.number_tv)
        addnumberbtn=findViewById(R.id.button)
        render()
        //send
        addnumberbtn.setOnClickListener{
            send()
        }
    }
    private fun send() {
        lifecycleScope.launch {
            viewModel.channels.send(MainIntent.addNumberIntent)

        }
    }
    //render
    private fun render(){
        lifecycleScope.launch {
            viewModel.state.collect{
                when(it){
                    is MainViewState.idle->{numbertv.text="Idle"}
                    is MainViewState.number->{ numbertv.text=it.num.toString()}
                    is MainViewState.error->{numbertv.text=it.err.toString()}

                }
            }
        }

    }
}