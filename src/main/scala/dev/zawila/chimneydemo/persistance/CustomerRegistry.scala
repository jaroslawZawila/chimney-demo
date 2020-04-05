package dev.zawila.chimneydemo.persistance

import dev.zawila.chimneydemo.model.Customer

trait CustomerRegistry[F[_]] {
  def getCustomers: F[List[Customer]]
  def persistCustomer(customer: Customer): F[Unit]
}
