package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import play.api.libs.json._
import java.util.Date

case class Weather(id: Int, temperature: Double, humidity: Int, pressure: Int, cloudsPercent: Int, created: Date) {

  def this(temperature: Double, humidity: Int, pressure: Int, cloudsPercent: Int, created: Date) = {
    this(0, temperature, humidity, pressure, cloudsPercent, created)
  }
}

private object parsers {

    val simple = {
    get[Int]("weather.id") ~
      get[Double]("weather.temperature") ~
      get[Int]("weather.humidity") ~
      get[Int]("weather.pressure") ~
      get[Int]("weather.clouds_percent") ~
      get[java.util.Date]("weather.create_dt") map {
      case id~temp~humidity~pressure~cloudsPercent~create_dt =>
          Weather(id, temp, humidity, pressure, cloudsPercent, create_dt)
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
          new Weather(0, 0.00, 0, 0, 0, new Date())
      }

    }
  }

  def list(): Seq[Weather] = {
    DB.withConnection { implicit c =>
      SQL("""
          select
            id, create_dt, temperature, humidity, pressure, clouds_percent
          from weather
            order by create_dt desc
      """.stripMargin)
      .as(parsers.simple *)
    }
  }

  def create(json: JsValue) {
    DB.withTransaction { implicit c =>
      SQL("""
        insert into weather (temperature, create_dt, humidity, pressure, wind_speed, wind_degree, clouds_percent)
        values ({temperature}, now(), {humidity}, {pressure}, {wind_speed}, {wind_degree}, {clouds_percent})
      """)
        .on('temperature -> (json \ "main" \ "temp").asOpt[Double].getOrElse(0.00),
          'humidity -> (json \ "main" \ "humidity").asOpt[Int].getOrElse(0),
          'pressure -> (json \ "main" \ "pressure").asOpt[Int].getOrElse(0),
          'wind_speed -> (json \ "wind" \ "speed").asOpt[Double].getOrElse(0.0),
          'wind_degree -> (json \ "wind" \ "deg").asOpt[Int].getOrElse(0),
          'clouds_percent -> (json \ "clouds" \ "all" ).asOpt[Int].getOrElse(0))
          .executeUpdate()
    }
  }

}
