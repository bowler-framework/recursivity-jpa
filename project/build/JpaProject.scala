import sbt._

class JpaProject(info: ProjectInfo) extends DefaultProject(info){
  val slf4jVersion = "1.6.0"
	
	val hibernateEntityManager = "org.hibernate" % "hibernate-entitymanager" % "3.5.1-Final"
	
	val recursivityCommons = "com.recursivity" % "recursivity-commons_2.8.1" % "0.4-SNAPSHOT"


  val sfl4japi = "org.slf4j" % "slf4j-api" % slf4jVersion
  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "runtime"
	
	val hsqldb = "hsqldb" % "hsqldb" % "1.8.0.7" % "test"
	
	val scalatest = "org.scalatest" % "scalatest" %
	    "1.2" % "test"
	val jbossRepo = "JBoss repo" at "http://repository.jboss.com/maven2/"
	
}