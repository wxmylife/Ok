package com.abby.library

class HttpUrl internal constructor(

  @get:JvmName("scheme") val scheme: String,
  @get:JvmName("username") val username: String,
  @get:JvmName("password") val password: String,
  @get:JvmName("host") val host: String,
  @get:JvmName("port") val port: Int,
  @get:JvmName("pathSegments") val pathSegments: List<String>,

  private val queryNamesAndValues: List<String?>?,
  @get:JvmName("fragment") val fragment: String?,
  private val url: String
) {
}