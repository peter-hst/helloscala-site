package com.helloscala.site.model

import com.helloscala.site.model.MyPostgresDriver.simple._
import org.joda.time.DateTime

/**
 * Created by Yang Jing on 2015-03-31.
 */
class Entities {

  class TableUser(tag: Tag) extends Table[User](tag, "m_user") {
    val id = column[String]("id", O.PrimaryKey)
    val createdAt = column[DateTime]("createdAt")

    def * = (id, createdAt) <>(User.tupled, User.unapply)
  }

  val tUser = TableQuery[TableUser]
}
