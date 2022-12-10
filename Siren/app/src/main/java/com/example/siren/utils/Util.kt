package com.example.siren.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun showLoading(progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE
}

fun stopLoading(progressBar: ProgressBar) {
    progressBar.visibility = View.GONE
}

fun toast(context: Context, msg: String): Toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
