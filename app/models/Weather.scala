package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import play.api.libs.json._
import java.util.Date
import play.api.Logger
import org.postgresql.util.PGobject

case class Weather(id: Int, created: Date, json: String) {

  def this(created: Date, json: String) = {
    this(0, created, json)
  }
}

private object parsers {

  val simple = {
    get[Int]("weather.id") ~
    get[Date]("weather.create_dt") ~
      get[JsValue]("weather.data") map {
        case id~create_dt~data =>
          val jsonTransformer = (__.json.update(__.read[JsObject].map { o =>
            o ++ Json.obj("dashboard_id" -> id, "dashboard_date" -> create_dt)
          }))
          (data.transform(jsonTransformer).getOrElse(Json.parse("{}"))).asInstanceOf[JsValue]
    }
  }

  implicit def rowToJsValue: Column[JsValue] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case pgo: PGobject => Right(Json.parse(pgo.getValue))
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" +
      value.asInstanceOf[AnyRef].getClass + " to JsValue for column " + qualified))
    }
  }

}

object Weather1 {

  val transformLogger: Logger = Logger("transform")

  val selectLatest = SQL("select * from weather order by create_dt desc limit 1")



  def latest(): JsValue = {
    DB.withConnection{ implicit c =>
      try {
        selectLatest.as(parsers.simple *).head
      } catch {
        case nse: NoSuchElementException =>
          Json.parse("{}")
      }

    }
  }

  def list(): Seq[JsValue] = {
    DB.withConnection { implicit c =>
      SQL("""
          select data from weather
            order by create_dt desc
      """.stripMargin)
      .as(parsers.simple *)
    }
  }

  def create(json: JsValue) {
    val pgObject = new PGobject()
    pgObject.setType("jsonb")
    pgObject.setValue(Some(json.toString).getOrElse("{}"))
    DB.withTransaction { implicit c =>
      SQL("""
        insert into weather (create_dt, data)
        values (now(), {data})
      """)
        .on('data -> anorm.Object(pgObject))
          .executeUpdate()
    }
  }

}
