package com.helloscala.site.data.domain

import com.helloscala.common.types.SortType
import com.helloscala.site.data.model.User

case class UserPage(items: Seq[User], total: Option[Int], query: UserPageQuery)

case class UserPageQuery(page: Int, size: Int, sort: SortType.Value)
