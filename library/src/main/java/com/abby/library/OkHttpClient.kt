package com.abby.library

open class OkHttpClient internal constructor(
  builder: Builder
) : Cloneable {


  class Builder constructor() {

    internal constructor(okHttpClient: OkHttpClient) : this() {

    }

  }

}