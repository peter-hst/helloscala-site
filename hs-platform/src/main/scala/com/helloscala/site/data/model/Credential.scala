package com.helloscala.site.data.model

import java.time.LocalDateTime

import com.helloscala.common.types.OwnerType
import com.helloscala.site.data.driver.MyDriver.api._

case class Credential(id: Option[Long],
                      email: Option[String],
                      salt: String,
                      password: String,
                      ownerType: OwnerType.Value,
                      createdAt: LocalDateTime)

class TableCredential(tag: Tag) extends Table[Credential](tag, "credential") {
  val id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  val email = column[Option[String]]("email")
  val salt = column[String]("salt")
  val password = column[String]("password")
  val ownerType = column[OwnerType.Value]("owner_type")
  val createdAt = column[LocalDateTime]("created_at")

  def __idxEmail = index(tableName + "_idx_email", email, true)

  def * = (id, email, salt, password, ownerType, createdAt) <>(Credential.tupled, Credential.unapply)
}

