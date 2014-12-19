package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class Weather(id: Pk[Long], temp: String)

object Weather {

  val simple = {
    get[Pk[Long]]("Weather.id") ~
    get[String]("Weather.temp") map {
      case id~temp => Map("id" -> id.get.toString, "temp" -> temp.toString)
    }
  }

  val results:List[Map[String,String]] = DB.withConnection { implicit c =>
    SQL("select * from Weather").as(Weather.simple *)
  }

}
