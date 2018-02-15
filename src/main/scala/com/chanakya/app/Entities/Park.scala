package com.chanakya.app.Entities

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._


/**
  * Created by rasingh on 2/15/18.
  */


case class ParkId(value: String) extends MappedTo[String]

case class Park(parkId: ParkId, parkName: String, url: Option[String], description: Option[String],
                fullName: String, directionsUrl: Option[String], designation: Option[String],
                weatherInfo: Option[String], parkCode: String, latitude: Option[Double],
                longitude: Option[Double], directionInfo: Option[String], states: Option[String])



class Parks(tag: Tag) extends Table[Park](tag, "PARKS") {
    // Columns
    def parkId = column[ParkId]("park_id", O.PrimaryKey, O.Length(60))
    def parkName = column[String]("park_name", O.Length(200))
    def url = column[Option[String]]("url", O.Length(500))
    def description = column[Option[String]]("description")
    def fullName = column[String]("full_name", O.Length(500))
    def directionsUrl = column[Option[String]]("directions_url", O.Length(500))
    def designation = column[Option[String]]("designation", O.Length(50))
    def weatherInfo = column[Option[String]]("weather_info")
    def parkCode = column[String]("park_code", O.Length(10))
    def latitude = column[Option[Double]]("latitude")
    def longitude = column[Option[Double]]("longitude")
    def directionInfo = column[Option[String]]("direction_info")
    def states = column[Option[String]]("states", O.Length(20))


    // Select
    def * = (parkId, parkName, url, description, fullName, directionsUrl, designation, weatherInfo, parkCode, latitude,
        longitude, directionInfo, states) <> (Park.tupled, Park.unapply)
}


