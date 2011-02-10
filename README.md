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

## Usage
Set up your JPA beans and persistence-units in your persistence.xml as you would normally.
If you want the "Jpa.entityManager" to return a valid EntityManager without having to pass the name of the persistence-unit you want to access by default, do the following:
	PersistenceUnit.unitName = "mypersistenceUnitName"

