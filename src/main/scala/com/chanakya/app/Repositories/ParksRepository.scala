package com.chanakya.app.Repositories

import com.chanakya.app.Entities.{Park, Parks}
import com.typesafe.scalalogging.LazyLogging
import slick.lifted.TableQuery
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._

/**
  * Created by rasingh on 2/15/18.
  */
class ParksRepository extends LazyLogging {
  val parks = TableQuery[Parks]
  //val url = ConfigReader.url + "/" + ConfigReader.database + "?user=" + ConfigReader.user + "&password=" + ConfigReader.password
  //lazy val db = Database.forURL(url, driver = ConfigReader.driver, executor = AsyncExecutor(ConfigReader.database, numThreads=5, queueSize=5000))

  def insert(records: List[Park]): Unit =  {
    val db = Database.forConfig("mysql")
    val setup = DBIO.seq(
      parks ++= records
      //parks.result.map(println)
    )
    try {
      Await.result(db.run(setup), Duration.Inf)
    } catch {
      case ex: Exception => println("Got exception in method insert() in ParksRepository: " + ex)
    } finally {
      db.close()
    }
  }
}