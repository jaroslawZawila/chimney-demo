package dev.zawila.chimneydemo.api

import cats.effect.Sync
import cats.implicits._
import dev.zawila.chimneydemo.model.Customer
import dev.zawila.chimneydemo.service.CustomerService
import org.http4s.dsl.Http4sDsl
import org.http4s.{Request, Response}

class CustomerRoutes[F[_]: Sync](customerService: CustomerService[F]) extends Http4sDsl[F] {

  val getCustomer: PartialFunction[Request[F], F[Response[F]]] = {
    case GET -> Root / "customers"  =>
      customerService.getCustomers().flatMap(c => Ok(Customers(c.map(CustomerApi.from))))
  }

  val createCustomer: PartialFunction[Request[F], F[Response[F]]] = {
    case req @ POST -> Root / "customer" =>
      for {
        customer <- req.as[Customer]
        _ <- customerService.saveCustomer(customer)
        resp <- Created()
      } yield resp
  }


}
