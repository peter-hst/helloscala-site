package com.helloscala.site.data.model

import java.time.{LocalDate, LocalDateTime}

import com.helloscala.common.types.GenderType
import com.helloscala.site.data.driver.MyDriver.api._

case class User(id: Long,
                email: Option[String],
                name: Option[String],
                nick: Option[String],
                gender: Option[GenderType.Value],
                birthday: Option[LocalDate],
                avatarUrl: Option[String],
                interest: Option[String],
                occupation: Option[String],
                createdAt: LocalDateTime = LocalDateTime.now())

private[data] class TableUser(tag: Tag) extends Table[User](tag, "user") {
  val id = column[Long]("id", O.PrimaryKey)
  val email = column[Option[String]]("email")
  val name = column[Option[String]]("name")
  val nick = column[Option[String]]("nick")
  val gender = column[Option[GenderType.Value]]("gender")
  val birthday = column[Option[LocalDate]]("birthday")
  val avatarUrl = column[Option[String]]("avatar_url")
  val interest = column[Option[String]]("interest")
  val occupation = column[Option[String]]("occupation")
  val createdAt = column[LocalDateTime]("created_at")

  def * = (id, email, name, nick, gender, birthday, avatarUrl, interest, occupation, createdAt
    ) <>(User.tupled, User.unapply)
}
