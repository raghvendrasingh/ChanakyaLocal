package com.chanakya.app

import scala.io.Source
import scala.collection.mutable.ListBuffer
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._


/**
  * Created by rasingh on 2/14/18.
  */
class DataFetchService {
  /** get park info */
  def getParkInfo(): List[Map[String, String]] = {
    val urlString = "https://developer.nps.gov/api/v1/parks?parkCode=acad,yell&api_key=A6jZoKoNUXeRfWoktuvo0sHrLVAhPbabY28bZoEs"
    val response = Source.fromURL(urlString).getLines.toList.head
    implicit val formats = org.json4s.DefaultFormats
    val result = ListBuffer[Map[String, Any]]()
    val parsedJson = parse(response)
    val numParks = (parsedJson\\"total").values.asInstanceOf[BigInt]
    val data = (parsedJson\\"data").values.asInstanceOf[List[Map[String, String]]]
    require(numParks == data.length)
    data
  }
}

object ExecuteDataFetchService extends App {
  val obj = new DataFetchService
  obj.getParkInfo()
}
