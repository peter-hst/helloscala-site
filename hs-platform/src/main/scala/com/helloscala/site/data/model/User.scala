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
                updatedAt: LocalDateTime)

private[data] class TableUser(tag: Tag) extends Table[User](tag, "user") {
  val id = column[Long]("id", O.PrimaryKey)
  val email = column[Option[String]]("email")
  val name = column[Option[String]]("name")
  val nick = column[Option[String]]("nick")
  val gender = column[Option[GenderType.Value]]("gender")
  val birthday = column[Option[LocalDate]]("birthday")
  val avatarUrl = column[Option[String]]("avatarUrl")
  val interest = column[Option[String]]("interest")
  val occupation = column[Option[String]]("occupation")
  val updatedAt = column[LocalDateTime]("updatedAt")

  def * = (id, email, name, nick, gender, birthday, avatarUrl, interest, occupation, updatedAt
    ) <>(User.tupled, User.unapply)
}
