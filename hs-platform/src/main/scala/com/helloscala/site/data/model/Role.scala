package com.helloscala.site.data.model

import java.time.LocalDateTime

import com.helloscala.site.data.driver.MyDriver.api._

/**
 * 角色
 * Created by jingyang on 15/5/30.
 */
case class Role(id: String,
                authorities: List[String],
                updatedAt: LocalDateTime)

class TableRole(tag: Tag) extends Table[Role](tag, "role") {
  val id = column[String]("id", O.PrimaryKey)
  val authorities = column[List[String]]("authorities")
  val updatedAt = column[LocalDateTime]("updatedAt")

  override def * = (id, authorities, updatedAt) <>(Role.tupled, Role.unapply)
}
