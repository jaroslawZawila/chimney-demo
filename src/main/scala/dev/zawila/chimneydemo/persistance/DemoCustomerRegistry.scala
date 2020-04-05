package dev.zawila.chimneydemo.persistance

import cats.effect.Sync
import cats.effect.concurrent.Ref
import cats.implicits._
import dev.zawila.chimneydemo.model.Customer

class DemoCustomerRegistry[F[_] : Sync] private (customers: Ref[F, Set[Customer]]) extends CustomerRegistry[F]{

  override def getCustomers: F[List[Customer]] =
    customers.get.map(_.toList)

  override def persistCustomer(customer: Customer): F[Unit] =
    customers.update { customers =>
      if (! customers.contains(customer))
        customers + customer
      else
        customers
    }
}

object DemoCustomerRegistry {
  def create[F[_] : Sync](): F[DemoCustomerRegistry[F]] = for {
    customers <- Ref.of[F, Set[Customer]](Set.empty)
    customerRegistry = new DemoCustomerRegistry[F](customers)
  } yield customerRegistry
}