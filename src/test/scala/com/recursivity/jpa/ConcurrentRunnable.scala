package com.recursivity.jpa

import Jpa._
import javax.persistence.EntityManager

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 10/02/2011
 * Time: 00:14
 * To change this template use File | Settings | File Templates.
 */

class ConcurrentRunnable extends Runnable{
  var em: EntityManager = null

  def getEm = em

  def run = {
    transaction{
      em = entityManager
    }
  }
}