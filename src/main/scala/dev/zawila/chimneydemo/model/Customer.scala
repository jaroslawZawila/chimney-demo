package dev.zawila.chimneydemo.model

import cats.effect.Sync
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe._
import io.circe.generic.auto._

case class Customer(name: String)

object Customer{
  implicit def encoder[F[_]: Sync]: EntityEncoder[F, Customer] = jsonEncoderOf[F, Customer]
  implicit def decoder[F[_]: Sync]: EntityDecoder[F, Customer] = jsonOf[F, Customer]
}
