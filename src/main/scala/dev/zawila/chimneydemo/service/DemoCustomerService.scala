package dev.zawila.chimneydemo.service

import cats.Functor
import dev.zawila.chimneydemo.model.Customer
import dev.zawila.chimneydemo.persistance.CustomerRegistry

class DemoCustomerService[F[_]: Functor](customerRegistry: CustomerRegistry[F]) extends CustomerService[F] {

  override def getCustomers(): F[List[Customer]] =
    customerRegistry.getCustomers

  override def saveCustomer(customer: Customer): F[Unit] =
    customerRegistry.persistCustomer(customer)
}
