#Recursivity JPA - Simple JPA utilities for Scala
This is a Scala:fied version of some simple JPA tools that Recursivity limited has used in production for various projects without any problems for the last 3-4 years. As such the code should be relatively mature and trouble free unless some bugs have crept in during the "translation process" from Java. We don't foresee this code changing massively, so don't expect massive updates - they're mostly not needed.
It would however be greatly appreciated if someone would want to contribute multi-phase commit support within the realms of the existing API.

## Features
* Works inside or outside a container - perfect for webapps and standalone apps alike.
* Allows transaction management within a closure/block like "transactional{..your code here}"
* Handles all the start, commit and rollback of a transaction (assuming you don't swallow exceptions that should force a rollback)
* Allows retrieval of an EntityManager from anywhere in code that is within a transaction with a simple function call.
* Transaction blocks that are contained within an existing transaction will join the existing transaction rather than create a new one
* No connection will be retrieved or actual transaction started until the first access of an actual EntityManager = excellent scalability, only use DB resources when they actually need to be used.
* Manages multiple JPA persistence-units (though does not do multi-phase commit, only commits all open transactions in some order, or rollsback those transactions that have yet to be committed.

## Maven/SBT setup
MVN/SBT repo: 
	https://oss.sonatype.org/content/repositories/releases
Dependency definition (sbt): 
	"com.recursivity" % "recursivity-jpa_2.8.1" % "1.0"
As different people might want to use different JPA providers, a default is not given. If you want to add Hibernate as your provided, you should add the following dependencies:
	val hibernateEntityManager = "org.hibernate" % "hibernate-entitymanager" % "3.5.1-Final" % "provided"
	val jbossRepo = "JBoss repo" at "http://repository.jboss.com/maven2/"
	val sfl4japi = "org.slf4j" % "slf4j-api" % "1.6.0" % "provided"
	val sfl4jnop = "org.slf4j" % "slf4j-nop" % "1.6.0" % "provided"

## Usage
Set up your JPA beans and persistence-units in your persistence.xml as you would normally.
If you want the "Jpa.entityManager" to return a valid EntityManager without having to pass the name of the persistence-unit you want to access by default, do the following:
	PersistenceUnit.unitName = "mypersistenceUnitName"
In places where you want to access JPA functionality easily, you just add the following import:
	import com.recursivity.jpa.Jpa._
To start a transaction/JPA persistence context, you do the following:
	transaction{
		..your code within the transaction/persistence context goes here..
	}
To get an EntityManager and access all JPA goodness anywhere in your code that runs within the above transaction block, including instances which are only indirectly called from the transaction block, simply have the import previously mentioned in your code and do the following:
	val em = entityManager // gets an EntityManager for the default persistence-unit we set in the PersistenceUnit.unitName
	..or:
	val em = entityManager("someOtherPersistenceUnit") // to retrieved a specific named persistence-unit defined in your persistence.xml
..and that's about it! Enjoy!

