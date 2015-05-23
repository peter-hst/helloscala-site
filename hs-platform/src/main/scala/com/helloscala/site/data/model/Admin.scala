package com.helloscala.site.data.model

import java.time.LocalDateTime

import com.helloscala.site.data.driver.MyDriver.api._

case class Admin(id: Long,
                 name: Option[String],
                 createdAt: LocalDateTime)

class TableAdmin(tag: Tag) extends Table[Admin](tag, "admin") {
  val id = column[Long]("id", O.PrimaryKey)
  val name = column[Option[String]]("name")
  val createdAt = column[LocalDateTime]("created_at")

  def * = (id, name, createdAt) <>(Admin.tupled, Admin.unapply)
}
