package com.recursivity.jpa

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import collection.mutable.HashMap

object PersistenceManagerFactory{
	val factories = new HashMap[String,EntityManagerFactory]
	val pFactory = PersistenceManagerFactory

	def apply(unitName: String): EntityManager = {
		if(factories.contains(unitName)){
			return factories.get(unitName).get.createEntityManager
		}else{
			val factory = Persistence.createEntityManagerFactory(unitName)
			factories.synchronized(factories.put(unitName, factory))
			return factory.createEntityManager
		}
	}
}