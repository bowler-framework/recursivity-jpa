package com.recursivity.jpa

import util.DynamicVariable
import collection.mutable.HashMap
import javax.persistence.{EntityTransaction, EntityManager}

/**
 * Created by IntelliJ IDEA.
 * User: wfaler
 * Date: 09/02/2011
 * Time: 23:35
 * To change this template use File | Settings | File Templates.
 */

object Jpa{
  private val _em = new DynamicVariable[HashMap[String, EntityManager]](null)
  private val _tx = new DynamicVariable[HashMap[EntityManager, EntityTransaction]](null)

  def  transaction(function: => Any): Any = {
    if(_em.value != null)
      return function
    else{
      _em.withValue(new HashMap[String, EntityManager]){
        _tx.withValue(new HashMap[EntityManager, EntityTransaction]){
          try{
            val any = function
            commit
            return any
          }catch{
            case e: Exception => {
              rollback
              throw e
            }
          }
        }
      }
    }
  }

  def entityManager: EntityManager = {
    entityManager(PersistenceUnit.unitName)
  }

  def entityManager(unit: String): EntityManager = {
    if(_em.value == null)
      throw new IllegalArgumentException("You can only get an EntityManager if you are within a transaction-closure context: transaction{..yourcode..}")
    if(_em.value.contains(unit)){
      return _em.value(unit)
    }else{
      val em = PersistenceManagerFactory(unit)
      _em.value.put(unit, em)
      val tx = em.getTransaction
      tx.begin
      _tx.value.put(em, tx)
      return em
    }
  }

  private def commit = {
     _em.value.keysIterator.foreach(k => {
       val em = _em.value(k)
       val tx = _tx.value(em)
       if(tx.isActive)
         tx.commit
       if(em.isOpen)
         em.close
     })
  }

  private def rollback = {
     _em.value.keysIterator.foreach(k => {
       val em = _em.value(k)
       val tx = _tx.value(em)
       if(tx.isActive)
         tx.commit
       if(em.isOpen)
         em.close
     })
  }

}
