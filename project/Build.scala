import sbt._
import Keys._

object WebContainerInfoExtractorBuild extends Build {

  val mbknorGithubRepoUrl = "http://mbknor.github.com/m2repo/releases/"
  val typesafeRepoUrl = "http://repo.typesafe.com/typesafe/releases/"

  lazy val WebContainerInfoExtractorProject = Project(
    "webcontainer-info-extractor",
    new File("."),
    settings = BuildSettings.buildSettings ++ Seq(
      libraryDependencies := Dependencies.runtime,
      publishMavenStyle := true,
      publishTo := Some(Resolvers.mbknorRepository),
      scalacOptions ++= Seq("-Xlint","-deprecation", "-unchecked","-encoding", "utf8"),
      javacOptions in (Compile) ++= Seq("-encoding", "utf8", "-g"),
      javacOptions in (doc) := Seq("-source", "1.5"),
      resolvers ++= Seq(DefaultMavenRepository
        , Resolvers.mbknorGithubRepo, Resolvers.typesafe
        )
    )
  )


  object Resolvers {
    val mbknorRepository = Resolver.ssh("my local mbknor repo", "localhost", "~/projects/mbknor/mbknor.github.com/m2repo/releases/")(Resolver.mavenStylePatterns)
    val mbknorGithubRepo = "mbknor github Repository" at mbknorGithubRepoUrl
    val typesafe = "Typesafe Repository" at typesafeRepoUrl
  }

  object Dependencies {

    val runtime = Seq(
    )
  }


  object BuildSettings {

    val buildOrganization = "com.kjetland"
    val buildVersion      = "1.1"
    val buildScalaVersion = "2.9.2"
    val buildSbtVersion   = "0.12"

    val buildSettings = Defaults.defaultSettings ++ Seq (
      organization   := buildOrganization,
      version        := buildVersion,
      scalaVersion   := buildScalaVersion,
      // Must ignore generating scaladoc since it gives an error
      publishArtifact in packageDoc := false
    )

  }


}

