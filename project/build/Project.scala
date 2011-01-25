import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) with IdeaProject
{
	
  override def managedStyle = ManagedStyle.Maven

  val publishTo = Resolver.ssh("my local mbknor repo", "localhost", "~/projects/mbknor.github.com/m2repo/releases/")

  Resolver.userMavenRoot

  //include doc and source in publish
  override def packageDocsJar = defaultJarPath("-javadoc.jar")
  override def packageSrcJar= defaultJarPath("-sources.jar")
  val sourceArtifact = Artifact.sources(artifactID)
  val docsArtifact = Artifact.javadoc(artifactID)
  override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageDocs, packageSrc)
  
	
  val scalaSnapshotRepo = "scala snapshot" at "http://scala-tools.org/repo-snapshots/"
}
