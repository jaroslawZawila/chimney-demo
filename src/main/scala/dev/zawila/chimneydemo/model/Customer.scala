package dev.zawila.chimneydemo.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import cats.effect.Sync
import io.circe.Decoder
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe._

import scala.util.Try

case class Customer(name: String, secretFiled: String, dob: LocalDate) {
  def age = 2020 - dob.getYear
}

object Customer{

  implicit val decodeInstant: Decoder[LocalDate] = Decoder.decodeString.emapTry { str =>
    Try(LocalDate.parse(str, DateTimeFormatter.ISO_DATE))
  }

  implicit def decoder[F[_]: Sync]: EntityDecoder[F, Customer] = jsonOf[F, Customer]
}
