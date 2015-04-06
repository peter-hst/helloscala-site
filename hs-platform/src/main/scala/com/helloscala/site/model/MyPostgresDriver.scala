package com.helloscala.site.model

import scala.slick.driver.PostgresDriver
import com.github.tminglei.slickpg._

/**
 * Created by Yang Jing on 2015-04-06.
 */
trait MyPostgresDriver
  extends PostgresDriver
  with PgDateSupportJoda
  //with PgRangeSupport
  with PgHStoreSupport
  with PgPlayJsonSupport
  //with PgSearchSupport
  //with PgPostGISSupport
  with PgArraySupport {

  override lazy val Implicit = new ImplicitsPlus {}
  override val simple = new SimpleQLPlus {}

  //////
  trait ImplicitsPlus
    extends Implicits
    with DateTimeImplicits
    //  with RangeImplicits
    with HStoreImplicits
    with JsonImplicits
    //  with SearchImplicits
    //  with PostGISImplicits
    with ArrayImplicits

  trait SimpleQLPlus
    extends SimpleQL
    //  with SearchAssistants
    //  with PostGISAssistants
    with ImplicitsPlus

}

object MyPostgresDriver extends MyPostgresDriver