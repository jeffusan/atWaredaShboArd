package actors

import akka.actor._
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scalaj.http.Http
import play.api.libs.json._


class RepoRetriever extends Actor with ActorLogging {

  import context.dispatcher

  val tick =
    context.system.scheduler.schedule(
      Duration(60, TimeUnit.SECONDS),
      Duration(20, TimeUnit.SECONDS),
      self,
      "commit"
    )

  override def postStop() = tick.cancel()

  def receive = {
    case message:String =>
      val commits = Http("https://api.github.com/repos/jeffusan/atWaredaShboArd/stats/contributors").asString
      val json: JsValue = Json.parse(commits.body)
      log.info("Json: => {}", json)

    case _ => log.info("unknown message")
  }

}
