package com.helloscala.site.data

import com.helloscala.common.TMessageResponse
import com.helloscala.common.types.{GenderType, OwnerType, SortType}
import com.helloscala.site.data.domain._
import com.helloscala.site.data.model.{Admin, Credential, User}
import play.api.libs.json._

trait JsonImplicits {
  implicit val __tMessageResponseWrites = new Writes[TMessageResponse] {
    override def writes(o: TMessageResponse): JsValue = Json.obj("code" -> o.code, "message" -> o.message)
  }
  implicit val __genderTypeFormats = new Format[GenderType.Value] {
    override def reads(json: JsValue): JsResult[GenderType.Value] = JsSuccess(GenderType.withName(json.as[String]))

    override def writes(o: GenderType.Value): JsValue = JsString(o.toString)
  }
  implicit val __sortTypeFormats = new Format[SortType.Value] {
    override def reads(json: JsValue): JsResult[SortType.Value] = JsSuccess(SortType.withName(json.as[String]))

    override def writes(o: SortType.Value): JsValue = JsString(o.toString)
  }
  implicit val __ownerTypeFormats = new Format[OwnerType.Value] {
    override def reads(json: JsValue): JsResult[OwnerType.Value] = JsSuccess(OwnerType.withName(json.as[String]))

    override def writes(o: OwnerType.Value): JsValue = JsString(o.toString)
  }

  implicit val __loginRequestFormats: Format[LoginParam] = Json.format[LoginParam]
  implicit val __registerRequestFormats: Format[RegisterParam] = Json.format[RegisterParam]
  implicit val __modifyPasswordParamFormats: Format[ModifyPasswordParam] = Json.format[ModifyPasswordParam]

  implicit val __credentialWrites: Writes[Credential] = new Writes[Credential] {
    override def writes(o: Credential): JsValue = {
      val s = Json.obj("id" -> o.id,
        "ownerType" -> o.ownerType,
        "email" -> o.email,
        "createdAt" -> o.createdAt).fields.filter(_._2 != JsNull)
      JsObject(s)
    }
  }
  implicit val __adminBeanFormats: Format[Admin] = Json.format[Admin]
  implicit val __userBeanFormats: Format[User] = Json.format[User]

  implicit val __adminPageQueryFormats: Writes[AdminPageQuery] = Json.writes[AdminPageQuery]
  implicit val __adminPageFormats: Writes[AdminPage] = Json.writes[AdminPage]

  implicit val __userPageQueryFormats: Writes[UserPageQuery] = Json.writes[UserPageQuery]
  implicit val __userPageFormats: Writes[UserPage] = Json.writes[UserPage]

  implicit val __fileUploadResult: Writes[FileUploadResult] = Json.writes[FileUploadResult]
}

object JsonImplicits extends JsonImplicits
