package dev.zawila.chimneydemo.api

import cats.effect.Sync
import cats.implicits._
import dev.zawila.chimneydemo.model.Customer
import dev.zawila.chimneydemo.service.CustomerService
import org.http4s.dsl.Http4sDsl
import org.http4s.{Request, Response}
import io.scalaland.chimney.dsl._

class CustomerRoutes[F[_]: Sync](customerService: CustomerService[F]) extends Http4sDsl[F] {

  val getCustomer: PartialFunction[Request[F], F[Response[F]]] = {
    case GET -> Root / "customers"  =>
      customerService.getCustomers().flatMap(c => Ok(Customers(c.map(_.into[CustomerApi].enableMethodAccessors.transform))))
  }

  val createCustomer: PartialFunction[Request[F], F[Response[F]]] = {
    case req @ POST -> Root / "customer" =>
      for {
        customer <- req.as[Customer]
        _ <- customerService.saveCustomer(customer)
        resp <- Created()
      } yield resp
  }

  val updateCustomerName: PartialFunction[Request[F], F[Response[F]]] = {
    case req @ POST -> Root / "update" / name =>
      for {
        request <- req.as[UpdateRequest]
        newCustomer <- customerService.changeName(name, request.name)
        respo <- Ok(newCustomer.into[CustomerApi].enableMethodAccessors.transform)
      } yield respo
  }


}
