package actors
package actors

import akka.actor._
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit

class WeatherRetriever extends Actor with ActorLogging {

  import context.dispatcher
  val tick =
    context.system.scheduler.schedule(
      Duration(5, TimeUnit.SECONDS),
      Duration(10, TimeUnit.SECONDS),
      self,
      "tick")

  override def postStop() = tick.cancel()

  def receive = {
    case message:String =>
      log.info("Message Received by Actor -> {}", message)
    case _ => log.info("unknown message")
  }
}
