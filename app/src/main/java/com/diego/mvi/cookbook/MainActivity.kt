package com.diego.mvi.cookbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diego.mvi.core.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}