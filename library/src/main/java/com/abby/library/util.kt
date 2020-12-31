@file:JvmName("Util")


package com.abby.library

import java.util.concurrent.ThreadFactory


fun threadFactory(
  name: String,
  daemon: Boolean
): ThreadFactory = ThreadFactory { runnable ->
  Thread(runnable, name).apply {
    isDaemon = daemon
  }
}

@JvmField
internal val okHttpName =
  OkHttpClient::class.java.name.removePrefix("com.").removeSuffix("Client")


@JvmField
internal val assertionsEnabled = OkHttpClient::class.java.desiredAssertionStatus()

@Suppress("NOTHING_TO_INLINE")
internal inline fun Any.assertThreadDoesntHoldLock() {
  if (assertionsEnabled && !Thread.holdsLock(this)) {
    throw AssertionError("Thread ${Thread.currentThread().name} MUST hold lock on $this")
  }
}