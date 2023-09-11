package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity

class MyApplication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MyApplication)
            modules()
        }}
        private fun modules() {
            TODO("Not yet implemented")
        }

        private fun androidContext(applicationDearDogs: MyApplication) {

        }

        private fun startKoin(function: () -> Unit) {


    }
}