package com.helloscala.site.model

import org.joda.time.DateTime

/**
 * Created by Yang Jing on 2015-03-31.
 */
case class User(id: String,
                createdAt: DateTime = DateTime.now()) {

}
