package com.chanakya.app.Services

import com.typesafe.config.ConfigFactory

/**
  * Created by rasingh on 2/15/18.
  */
object ConfigReader {
  lazy val config = ConfigFactory.load()
  lazy val authKey = config.getString("is.auth_key")
  lazy val driver = config.getString("is.db.driver")
  lazy val url = config.getString("is.db.db.url")
  lazy val user = config.getString("is.db.db.user")
  lazy val password = config.getString("is.db.db.password")
  lazy val database = config.getString("is.db.db.database")
}
