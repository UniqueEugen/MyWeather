package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MyApplication)
            modules()
        }}
        private fun modules() {

        }

        private fun androidContext(applicationDearDogs: MyApplication) {

        }*/

        private fun startKoin(function: () -> Unit) {


    }
}