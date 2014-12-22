package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class Weather(id: Id[Int], temp: String)

object Weather {

  val simple = {
    get[Int]("Weather.id") ~
    get[String]("Weather.temp") map {
      case id~temp => Map("id" -> id.toString, "temp" -> temp.toString)
    }
  }

  val selectLatest = SQL("select * from weather order by id desc limit 1")

  val latest = DB.withConnection{ implicit c =>
    selectLatest.as(Weather.simple *).head
  }

}
