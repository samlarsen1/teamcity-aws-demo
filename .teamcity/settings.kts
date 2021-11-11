import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

import projects.network.Network

version = "2021.4"
description = "TC Terraform AWS Demo"
project(ProjecT)

object Project : Project({

    params{
        param("aws_region", "us-east-1")
    }

    subProject(Dev)
    subProject(Test)
    buildType(Build)
})

object Build : BuildType({
    name = "Build"
    description = "Multi environment terraform build"
    vcs {
        root(DslContext.settingsRoot)
    }

    triggers {
        vcs {
        }
    }
id("Build")
steps {
  script {
    name = "Set version using script"
    scriptContent = """
      #!/bin/bash
      HASH=%build.vcs.number%
      SHORT_HASH=${"$"}{HASH:0:7}
      BUILD_COUNTER=%build.counter%
      BUILD_NUMBER="1.0${"$"}BUILD_COUNTER.${"$"}SHORT_HASH"
      echo "##teamcity[buildNumber '${"$"}BUILD_NUMBER']"
      """.trimIndent()
  }
  script {
      name "check OS verions"
      scriptContent = """
      cat /etc/os-release
      lsb_release -a
      uname -r
      "
  }
  script {
    name = "build"
    scriptContent = """
      mkdir bin
      echo "built artifact" > bin/compiled.txt
      """.trimIndent()
  }
}

})
val Dev  = createEnvironment( "Dev",  "dev",  "dev",  "dev",  "_AWS_ACCOUNT_ID_" )
val Test = createEnvironment( "Prod", "prod", "prod", "prod", "_AWS_ACCOUNT_ID_" )

fun createEnvironment(envTitle: String, envKey: String, namespace: String, 
    branchName: String, envAccountId: String): Project {
    //val infraVCSRoot
}