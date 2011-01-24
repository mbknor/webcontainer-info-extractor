import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) with IdeaProject
{
	val scalaSnapshotRepo = "scala snapshot" at "http://scala-tools.org/repo-snapshots/"
}
