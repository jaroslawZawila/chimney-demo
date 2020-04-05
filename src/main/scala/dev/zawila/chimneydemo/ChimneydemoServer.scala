package dev.zawila.chimneydemo

import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, Timer}
import dev.zawila.chimneydemo.api.HttpRoute
import dev.zawila.chimneydemo.service.CustomerService
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{RequestLogger, ResponseLogger}


class ChimneydemoServer[F[_]: ConcurrentEffect: Timer](customerService: CustomerService[F]) {
  def stream()(implicit C: ContextShift[F]): fs2.Stream[F, ExitCode] = {
    val router: HttpRoute[F] = new HttpRoute[F](customerService)
    val app = Router("/" -> router.routes).orNotFound

    val appWithMiddlewares =
      ResponseLogger.httpApp(logBody = false, logHeaders = false)(
        RequestLogger.httpApp(logBody = false, logHeaders = false)(app))

      ResponseLogger.httpApp(logHeaders = false, logBody = false)(
      RequestLogger.httpApp(logHeaders = false, logBody = false)(app))

    BlazeServerBuilder[F]
      .bindHttp(8021, "0.0.0.0")
      .withHttpApp(appWithMiddlewares)
      .serve
  }
}