package com.helloscala.site.data.model

import com.helloscala.site.data.driver.MyDriver.api._

private[data] object Schemas {
  val db = Database.forConfig("helloscala.db")

  def tRole = TableQuery[TableRole]

  def tCredential = TableQuery[TableCredential]

  def tUser = TableQuery[TableUser]

  def schemas =
    tRole.schema ++
      tCredential.schema ++
      tUser.schema
}
