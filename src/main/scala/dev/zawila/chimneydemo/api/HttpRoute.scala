package dev.zawila.chimneydemo.api

import cats.effect.Sync
import dev.zawila.chimneydemo.service.CustomerService
import org.http4s.HttpRoutes

class HttpRoute[F[_] : Sync](customerService: CustomerService[F]) {

  val customerRoutes = new CustomerRoutes[F](customerService)

  val routes: HttpRoutes[F] = HttpRoutes.of({
    customerRoutes.createCustomer.orElse(
      customerRoutes.getCustomer
    ).orElse(
      customerRoutes.updateCustomerName
    )
  })
}
