package com.recursivity.jpa

import Jpa._
import org.scalatest.FunSuite

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 10/02/2011
 * Time: 00:09
 * To change this template use File | Settings | File Templates.
 */

class JpaTest extends FunSuite{
  PersistenceUnit.unitName = "jpa-utils";

  test("get entityManager"){
    transaction{
      assert(entityManager != null)
      println("finishing tx")
    }

  }

  test("no tx"){
    try{
      entityManager
      fail("should throw illegal argument exception")
    }catch{
      case e: IllegalArgumentException =>{}// do nothing, expected
    }
  }

  test("test concurrency"){
    val c1 = new ConcurrentRunnable
		val c2 = new ConcurrentRunnable
		new Thread(c1).start
		Thread.sleep(50)
		new Thread(c2).start
		Thread.sleep(500)
		//assert(1 == 2)
		assert(c1.getEm != null)
		assert(c2.getEm != null)
		assert(!c1.getEm.equals(c2.getEm))

  }

  test("test persistence with bean"){
    val myBean = new MyBean
		myBean.id = "key"
		myBean.value ="Value"
    transaction{
      entityManager.persist(myBean)
    }

    transaction{
      val bean = entityManager.find(classOf[MyBean], "key")
      assert(bean != null)
      assert(bean.value == "Value")
    }
  }

}