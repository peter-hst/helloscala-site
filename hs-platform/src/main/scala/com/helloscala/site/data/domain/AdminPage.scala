package com.helloscala.site.data.domain

import com.helloscala.site.data.model.Admin

case class AdminPage(items: Seq[Admin], total: Option[Int], query: AdminPageQuery)

case class AdminPageQuery(page: Int, size: Int)