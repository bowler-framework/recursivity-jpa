package com.recursivity.jpa

import javax.persistence.{Entity, Table, Id, Column}
import reflect.BeanProperty

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 10/02/2011
 * Time: 00:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="test_beans")
class MyBean{
  @Id
  @Column
  @BeanProperty
  var id: String = null
  @Column
  @BeanProperty
  var value: String = null
}