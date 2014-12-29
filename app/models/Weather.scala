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
    get[JsValue]("row_to_json") map {
      case row_to_json =>
        row_to_json.asInstanceOf[JsValue]
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

  val selectLatest = SQL("""
select row_to_json(row)
     from (
     select
     (data #>> '{main,temp}')::NUMERIC -273 as temperature,
     data #>> '{main,humidity}' as humidity,
     data #>> '{main,pressure}' as pressure,
     data #>> '{clouds,all}' as clouds
     from weather
     order by create_dt desc
     limit 1) row
""")

  val selectHistory = SQL("""
select row_to_json(row)
from(
  select
  date_trunc('hour', create_dt) as period,
  (data #>> '{main,temp}')::NUMERIC - 273 as temperature,
  data #>> '{main,humidity}' as humidity,
  data #>> '{main,pressure}' as pressure
  from weather
  order by create_dt desc
  limit 10)
row""")

  val create = SQL("""
        insert into weather (create_dt, data)
        values (now(), {data})
      """)

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

  def history(): Seq[JsValue] = {
    DB.withConnection { implicit c =>
      try {
        selectHistory.as(parsers.simple *)
      } catch {
        case nse: NoSuchElementException =>
          Seq(Json.parse("{}"))
      }
    }
  }

  def create(json: JsValue) {
    val pgObject = new PGobject()
    pgObject.setType("jsonb")
    pgObject.setValue(Some(json.toString).getOrElse("{}"))
    DB.withTransaction { implicit c =>
        create.on('data -> anorm.Object(pgObject)).executeUpdate()
    }
  }

}
