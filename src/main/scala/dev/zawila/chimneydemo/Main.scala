package dev.zawila.chimneydemo

import cats.effect.{ExitCode, IO, IOApp}
import dev.zawila.chimneydemo.persistance.DemoCustomerRegistry
import dev.zawila.chimneydemo.service.DemoCustomerService

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    for {
      customerRegistry <- DemoCustomerRegistry.create[IO]()
      customerService = new DemoCustomerService[IO](customerRegistry)
      _ <- new ChimneydemoServer[IO](customerService).stream.compile.drain
    } yield ExitCode.Success
  }
}