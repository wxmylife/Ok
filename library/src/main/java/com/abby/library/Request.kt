package com.abby.library

class Request internal constructor(
  @get:JvmName("url") val url: HttpUrl
) {
}