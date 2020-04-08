package dev.zawila.chimneydemo.service

import cats.Monad
import cats.implicits._
import dev.zawila.chimneydemo.model.Customer
import dev.zawila.chimneydemo.persistance.CustomerRegistry

class DemoCustomerService[F[_]: Monad](customerRegistry: CustomerRegistry[F]) extends CustomerService[F] {

  override def getCustomers(): F[List[Customer]] =
    customerRegistry.getCustomers

  override def saveCustomer(customer: Customer): F[Unit] =
    customerRegistry.persistCustomer(customer)

  override def changeName(oldName: String, newName: String): F[Customer] = for {
    customer <- customerRegistry.getCustomers.map(_.find(_.name == oldName).get)
    newCustomer = Customer(newName, customer.secretFiled, customer.dob)
    _ <- customerRegistry.persistCustomer(newCustomer)
  } yield newCustomer
}
