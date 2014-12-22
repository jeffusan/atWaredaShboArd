package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class Weather(id: Int, temp: String) {

  def this(temp: String) = {
    this(0, temp)
  }
}

private object parsers {

    val simple = {
    get[Int]("weather.id") ~
    get[String]("weather.temp") map {
      case id~temp =>
        Weather(id, temp)
    }
  }

}

object Weather1 {

  val selectLatest = SQL("select * from weather order by id desc limit 1")

  def latest(): Weather = {
    DB.withConnection{ implicit c =>
      try {
        selectLatest.as(parsers.simple *).head
      } catch {
        case nse: NoSuchElementException =>
          new Weather(0, "0.00")
      }

    }
  }

  def list(): Seq[Weather] = {
    DB.withConnection { implicit c =>
      SQL("""
          select
            temp
          from weather
            order by id
      """.stripMargin)
      .as(parsers.simple *)
    }
  }

  def create(temp: String) {
    DB.withTransaction { implicit c =>
      SQL("insert into weather (temp) values ({temp})")
      .on('temp -> temp).executeUpdate()
    }
  }

}
