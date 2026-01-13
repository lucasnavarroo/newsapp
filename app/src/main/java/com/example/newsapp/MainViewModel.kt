package com.example.newsapp

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var authenticated = false

    fun shouldAuthenticate(): Boolean = !authenticated

    fun onAuthenticated() {
        authenticated = true
    }
}
