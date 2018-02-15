package com.chanakya.app.Services

import com.chanakya.app.Entities.{Park, ParkId}
import com.chanakya.app.Repositories.ParksRepository
import com.typesafe.config.ConfigFactory
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.collection.mutable.ListBuffer
import scala.io.Source


/**
  * Created by rasingh on 2/14/18.
  */
class DataFetchService {

  private def convertToParkCaseClass(data: List[Map[String, String]]): List[Park] = {
    val parks = ListBuffer[Park]()
    for (rec <- data) {
      val url = rec("url") match {
        case str if str.nonEmpty => Some(str)
        case str if str.isEmpty => None
      }

      val description = rec("description") match {
        case str if str.nonEmpty => Some(str)
        case str if str.isEmpty => None
      }

      val directionsUrl = rec("directionsUrl") match {
        case str if str.nonEmpty => Some(str)
        case str if str.isEmpty => None
      }

      val designation = rec("designation") match {
        case str if str.nonEmpty => Some(str)
        case str if str.isEmpty => None
      }

      val weatherInfo = rec("weatherInfo") match {
        case str if str.nonEmpty => Some(str)
        case str if str.isEmpty => None
      }

      val (lat, long) = rec("latLong") match {
        case str if str.nonEmpty => {
          val mp = str.split(",").map(x => x.split(":")).map(arr => arr.head->arr.last).toMap
          val lati = mp.getOrElse("lat","").trim match {
            case x if x.nonEmpty => Some(x.toDouble)
            case x if x.isEmpty => None
          }
          val longi = mp.getOrElse("long","").trim match {
            case x if x.nonEmpty => Some(x.toDouble)
            case x if x.isEmpty => None
          }
          (lati, longi)
        }
        case str if str.isEmpty => (None, None)
      }

      val directionsInfo = rec("directionsInfo") match {
        case str if str.nonEmpty => Some(str)
        case str if str.isEmpty => None
      }

      val states = rec("states") match {
        case str if str.nonEmpty => Some(str)
        case str if str.isEmpty => None
      }



      parks += Park(ParkId(rec("id")), rec("name"), url, description, rec("fullName"), directionsUrl, designation,
        weatherInfo, rec("parkCode"), lat, long, directionsInfo, states)
    }
    parks.toList
  }

  /** get park info */
  def getParkInfo(parkCodes: String): List[Park] = {
    val authKey = ConfigReader.authKey
    val urlString = s"https://developer.nps.gov/api/v1/parks?parkCode=${parkCodes}&api_key=${authKey}"
    val response = Source.fromURL(urlString).getLines.toList.head
    implicit val formats = org.json4s.DefaultFormats
    val result = ListBuffer[Map[String, Any]]()
    val parsedJson = parse(response)
    val numParks = (parsedJson\\"total").values.asInstanceOf[BigInt]
    val data = (parsedJson\\"data").values.asInstanceOf[List[Map[String, String]]]
    require(numParks == data.length)
    convertToParkCaseClass(data)
  }

  def fetchParkInfoAndInsertIntoDatabase(parkCodes: String = ""): Unit = {
    val parksRepo = new ParksRepository
    val parks = parkCodes match {
      case x if x.nonEmpty => getParkInfo(parkCodes)
      case x if x.isEmpty => getParkInfo(parkCodes) // change it
      case _ => throw new Exception(s"Invalid park codes: ${parkCodes}")
    }
    for (park <- parks) parksRepo.insert(park)
  }
}

object ExecuteDataFetchService extends App {
  val obj = new DataFetchService
  val parkCodes = "acad"
  obj.fetchParkInfoAndInsertIntoDatabase(parkCodes)
}