package com.bsuir.zhlobin.uniquekurankouyauhen.myapplication.screens

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.content.Context


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeDialog(){
    val openDialog = remember { mutableStateOf(false) }
    /*val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder
        .setMessage("I am the message")
        .setTitle("I am the title")
        .setPositiveButton("Positive") { dialog, which ->
            // Do something.
        }
        .setNegativeButton("Negative") { dialog, which ->
            // Do something else.
        }

    val dialog: AlertDialog = builder.create()
    dialog.show()*/
}

