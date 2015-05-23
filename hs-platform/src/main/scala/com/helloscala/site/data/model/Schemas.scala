package com.helloscala.site.data.model

import com.helloscala.site.data.driver.MyDriver.api._

private[data] object Schemas {
  val db = Database.forConfig("helloscala.db")

  def tCredential = TableQuery[TableCredential]

  def tAdmin = TableQuery[TableAdmin]

  def tUser = TableQuery[TableUser]

  def schemas =
    tCredential.schema ++
      tAdmin.schema ++
      tUser.schema
}
