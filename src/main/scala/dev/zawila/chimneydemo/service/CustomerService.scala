package dev.zawila.chimneydemo.service

import dev.zawila.chimneydemo.model.Customer

trait CustomerService[F[_]] {

  def getCustomers(): F[List[Customer]]
  def saveCustomer(customer: Customer): F[Unit]
  def changeName(oldName: String, newName: String): F[Customer]
}

