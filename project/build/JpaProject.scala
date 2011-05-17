import sbt._

class JpaProject(info: ProjectInfo) extends DefaultProject(info){//} with ChecksumPlugin{	
  val slf4jVersion = "1.6.0"
	
  val hibernateEntityManager = "org.hibernate" % "hibernate-entitymanager" % "3.6.1.Final" % "provided"


  val sfl4japi = "org.slf4j" % "slf4j-api" % slf4jVersion % "provided"
  val sfl4jnop = "org.slf4j" % "slf4j-nop" % slf4jVersion % "provided"
	
  val hsqldb = "hsqldb" % "hsqldb" % "1.8.0.7" % "test"
	
  val scalatest = {
    if(buildScalaVersion.contains("2.9"))
      "org.scalatest" %% "scalatest" % "1.4.1" % "test"
    else
      "org.scalatest" % "scalatest" % "1.3" % "test"
  }
  val jbossRepo = "JBoss repo" at "https://repository.jboss.org/nexus/content/repositories/releases/"
	
	
	 Credentials(Path.userHome / ".ivy2" / ".credentials", log)

	  val publishTo = {
		if(version.toString.endsWith("-SNAPSHOT"))
			"Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
		else "Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
	  }

	  override def managedStyle = ManagedStyle.Maven

	  override def deliverProjectDependencies = Nil
		override def packageDocsJar = defaultJarPath("-javadoc.jar")
	  override def packageSrcJar= defaultJarPath("-sources.jar")
	  lazy val sourceArtifact = Artifact.sources(artifactID)
	  lazy val docsArtifact = Artifact.javadoc(artifactID)

	override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageDocs, packageSrc)
	 override def pomExtra = {

	    // If these aren't lazy, then the build crashes looking for
	    // ${moduleName}/project/build.properties.


		(
		    <name>{name}</name>
		    <description>Recursivity JPA Project POM</description>
		    <url>http://github.com/wfaler/recursivity-jpa</url>
		    <inceptionYear>2010</inceptionYear>
		    <organization>
		      <name>Recursivity Commons Project</name>
		      <url>http://github.com/wfaler/recursivity-jpa</url>
		    </organization>
		    <licenses>
		      <license>
		        <name>BSD</name>
		        <url>http://github.com/wfaler/recursivity-jpa/LICENSE</url>
		        <distribution>repo</distribution>
		      </license>
		    </licenses>
		    <scm>
		      <connection>scm:git:git://github.com/wfaler/recursivity-jpa.git</connection>
		      <url>http://github.com/wfaler/recursivity-jpa</url>
		    </scm>
		    <developers>
		      <developer>
		        <id>wfaler</id>
		        <name>Wille Faler</name>
		        <url>http://blog.recursivity.com</url>
		      </developer>
		    </developers>)
	  }
	
}