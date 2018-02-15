package com.chanakya.app.Repositories
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.chanakya.app.Entities.{Park, Parks}
import com.chanakya.app.Services.ConfigReader
import slick.lifted.TableQuery
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import slick.jdbc.MySQLProfile.api._

/**
  * Created by rasingh on 2/15/18.
  */
class ParksRepository {
  lazy val parks = TableQuery[Parks]
  //val url = ConfigReader.url + "/" + ConfigReader.database + "?user=" + ConfigReader.user + "&password=" + ConfigReader.password
  //lazy val db = Database.forURL(url, driver = ConfigReader.driver, executor = AsyncExecutor(ConfigReader.database, numThreads=5, queueSize=5000))
  val db = Database.forConfig("is.mysql")
  println("db = " + db)

  def insert(record: Park): Unit =  {
    try {
      val seq1 = DBIO.seq(parks += record)
      println("seq1 = " + seq1)
      Await.result(db.run(seq1), Duration.Inf)
    } catch {
      case ex: Exception => println("Got exception in method insert() in ParksRepository: " + ex)
    } finally {
      println("closing db")
      db.close
    }
  }
}