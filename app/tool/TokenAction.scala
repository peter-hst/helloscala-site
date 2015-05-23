package tool

import com.helloscala.common.OwnerToken
import com.helloscala.common.exception.HsUnauthorizedException
import com.helloscala.common.types.OwnerType
import com.typesafe.scalalogging.StrictLogging
import play.api.mvc.{ActionBuilder, ActionTransformer, Request, WrappedRequest}

import scala.concurrent.Future

class TokenRequest[A](val ownerToken: OwnerToken, request: Request[A]) extends WrappedRequest[A](request) {
  override def toString() = s"TokenRequest($ownerToken)" + super.toString
}

object TokenAction extends ActionBuilder[TokenRequest] with ActionTransformer[Request, TokenRequest] with StrictLogging {
  override protected def transform[A](request: Request[A]): Future[TokenRequest[A]] = {
    WebTools.getOwnerToken(request) match {
      case Some(token) =>
        logger.debug(token.toString)

        if (token.hasExpired) {
          throw HsUnauthorizedException("token timeout")
        }

        Future.successful(new TokenRequest(token, request))

      case None =>
        throw HsUnauthorizedException("token not exists")
    }
  }
}

object AdminAction extends ActionBuilder[TokenRequest] with ActionTransformer[Request, TokenRequest] with StrictLogging {
  override protected def transform[A](request: Request[A]): Future[TokenRequest[A]] = {
    WebTools.getOwnerToken(request) match {
      case Some(token) =>
        logger.debug(token.toString)

        if (token.ownerType != OwnerType.ADMIN) {
          throw HsUnauthorizedException("token must be ADMIN")
        }

        if (token.hasExpired) {
          throw HsUnauthorizedException("token timeout")
        }

        Future.successful(new TokenRequest(token, request))

      case None =>
        throw HsUnauthorizedException("token not exists")
    }
  }
}
