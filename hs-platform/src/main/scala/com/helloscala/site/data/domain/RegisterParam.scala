package com.helloscala.site.data.domain

trait BaseAuthParam {
  def account: String

  def password: String
}

case class RegisterParam(account: String, password: String) extends BaseAuthParam

case class LoginParam(account: String, password: String, captcha: String) extends BaseAuthParam
