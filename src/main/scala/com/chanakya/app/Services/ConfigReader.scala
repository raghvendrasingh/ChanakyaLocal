package com.chanakya.app.Services

import com.typesafe.config.ConfigFactory

/**
  * Created by rasingh on 2/15/18.
  */
object ConfigReader {
  lazy val config = ConfigFactory.load()
  lazy val authKey = config.getString("auth_key")
}
