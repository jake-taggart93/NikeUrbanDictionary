package com.example.urbandictionary.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes

fun Context.makeLongToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun ViewGroup.inflate(@LayoutRes layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)