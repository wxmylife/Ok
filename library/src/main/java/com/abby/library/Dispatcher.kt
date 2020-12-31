package com.abby.library

import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Dispatcher constructor() {


  @get:Synchronized
  var maxRequests = 64
    set(maxRequests) {
      require(maxRequests >= 1) { "max < 1: $maxRequests" }
      synchronized(this) {
        field = maxRequests
      }
//      promoteAndExecute()
    }


  @get:Synchronized
  var maxRequestsPerHost = 5
    set(maxRequestsPerHost) {
      require(maxRequestsPerHost >= 1) { "max < 1:  $maxRequestsPerHost" }
      synchronized(this) {
        field = maxRequestsPerHost
      }
//      promoteAndExecute()
    }

  @set:Synchronized
  @get:Synchronized
  var idleCallback: Runnable? = null

  private var executorServiceOrNull: ExecutorService? = null

  @get:Synchronized
  @get:JvmName("executorService")
  val executorService: ExecutorService
    get() {
      if (executorServiceOrNull == null) {
        executorServiceOrNull = ThreadPoolExecutor(
          0, Int.MAX_VALUE, 60, TimeUnit.SECONDS,
          SynchronousQueue(), threadFactory("$okHttpName Dispatcher", false)
        )
      }
      return executorServiceOrNull!!
    }

  constructor(executorService: ExecutorService) : this() {
    this.executorServiceOrNull = executorService
  }

  private fun <T> finished(calls: Deque<T>, call: T) {
    val idleCallback: Runnable?
    synchronized(this) {
      if (!calls.remove(call)) {
        throw AssertionError("Call wasn't in-flight!")
      }
      idleCallback = this.idleCallback
    }

    val isRunning = promoteAndExecute()
    if (!isRunning && idleCallback != null) {
      idleCallback.run()
    }
  }

  private fun promoteAndExecute(): Boolean {
    this.assertThreadDoesntHoldLock()
    return false
  }
}