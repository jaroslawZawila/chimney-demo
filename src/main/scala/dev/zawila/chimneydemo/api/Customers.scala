package dev.zawila.chimneydemo.api

import cats.effect.Sync
import io.circe.generic.auto._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}

case class Customers(customers: List[CustomerApi])

case class CustomerApi(name: String, age: Int)

case class UpdateRequest(name: String)

object Customers {
  implicit def encoder[F[_]: Sync]: EntityEncoder[F, Customers] = jsonEncoderOf[F, Customers]
  implicit def decoder[F[_]: Sync]: EntityDecoder[F, Customers] = jsonOf[F, Customers]
}

object CustomerApi {
  implicit def decoder[F[_]: Sync]: EntityDecoder[F, CustomerApi] = jsonOf[F, CustomerApi]
  implicit def encoder[F[_]: Sync]: EntityEncoder[F, CustomerApi] = jsonEncoderOf[F, CustomerApi]
}

object UpdateRequest {
  implicit def decoder[F[_]: Sync]: EntityDecoder[F, UpdateRequest] = jsonOf[F, UpdateRequest]
  implicit def encoder[F[_]: Sync]: EntityEncoder[F, UpdateRequest] = jsonEncoderOf[F, UpdateRequest]
}
