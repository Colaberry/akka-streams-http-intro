package trades

import java.time.Instant
import java.time.format.DateTimeParseException

import spray.json._

trait JsonProtocol extends DefaultJsonProtocol {

  // Instant serialization
  implicit object InstantJsonFormat extends JsonFormat[Instant] {
    override def write(instant: Instant) = JsString(instant.toString)
    override def read(json: JsValue) = json match {
      case JsString(s) => try {
        Instant.parse(s)
      } catch {
        case e: DateTimeParseException => deserializationError("invalid Instant string", e)
      }
      case _ => deserializationError("Instant string expected")
    }
  }

  implicit val intervalFormat = jsonFormat2(Interval.apply)
  implicit val ohlcvFormat = jsonFormat8(OHLCV.apply)

}

object JsonProtocol extends JsonProtocol
