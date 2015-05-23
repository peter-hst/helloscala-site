package com.helloscala.common.exception

import com.helloscala.common.TMessageResponse

abstract class HsBaseException(val message: String, val code: Int) extends RuntimeException(message) with TMessageResponse {
  override def toString = code + ": " + super.toString
}

case class HsException(override val code: Int,
                       override val message: String) extends HsBaseException(message, code)

case class HsUnauthorizedException(override val message: String = "Unauthorized",
                                   override val code: Int = 401001) extends HsBaseException(message, code)

case class HsBadException(override val message: String = "Unauthorized",
                          override val code: Int = 400001) extends HsBaseException(message, code)

case class HsInternalException(override val message: String = "Internal Exception in Fenjoy",
                               override val code: Int = 500001) extends HsBaseException(message, code)

case class HsNotFoundException(override val message: String = "Not Found",
                               override val code: Int = 404001) extends HsBaseException(message, code)
