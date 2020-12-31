package com.abby.ok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abby.library.Dispatcher

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val dispatcher = Dispatcher()
    dispatcher.maxRequests = 1
  }
}
