package com.helloscala.site.data.driver

import com.github.tminglei.slickpg._
import com.helloscala.common.HsConstant
import com.helloscala.common.types.{GenderType, MsgStatus, OwnerType}
import play.api.libs.json.{JsValue, Json}
import slick.driver.JdbcDriver

trait MyColumnTypes {
  this: JdbcDriver =>

  trait MyColumnsAPI {
    self: API =>
    implicit val __ownerTypeColumnType = MappedColumnType.base[OwnerType.Value, String]({ o => o.toString }, { s => OwnerType.withName(s) })
    implicit val __genderTypeColumnType = MappedColumnType.base[GenderType.Value, String]({ o => o.toString }, { s => GenderType.withName(s) })
    implicit val __remindStatusColumnType = MappedColumnType.base[MsgStatus.Value, String]({ o => o.toString }, { s => MsgStatus.withName(s) })
  }

}

private[data] trait MyDriver
  extends ExPostgresDriver
  with PgDate2Support
  with PgHStoreSupport
  with PgPlayJsonSupport
  with PgArraySupport
  //with PgRangeSupport
  //with PgSearchSupport
  //with PgPostGISSupport
  with MyColumnTypes {
  override val pgjson = "jsonb"
  override val api = MyAPI

  //////
  object MyAPI
    extends API
    with DateTimeImplicits
    with HStoreImplicits
    with JsonImplicits
    with ArrayImplicits
    //  with RangeImplicits
    //  with SearchImplicits
    //  with PostGISImplicits
    with MyColumnsAPI {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val json4sJsonArrayTypeMapper =
      new AdvancedArrayJdbcType[JsValue](pgjson,
        (s) => utils.SimpleArrayUtils.fromString[JsValue](Json.parse)(s).orNull,
        (v) => utils.SimpleArrayUtils.mkString[JsValue](_.toString())(v)
      ).to(_.toList)

  }

  object SqlTypes {
    val OID = "char(" + HsConstant.OID_LENGTH + ")"
  }

}

private[data] object MyDriver extends MyDriver
