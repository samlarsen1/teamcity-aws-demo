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

    vcs {
        root(DslContext.settingsRoot)
    }

    triggers {
        vcs {
        }
    }
})
val Dev  = createEnvironment( "Dev",  "dev",  "dev",  "dev",  "_AWS_ACCOUNT_ID_" )
val Test = createEnvironment( "Prod", "prod", "prod", "prod", "_AWS_ACCOUNT_ID_" )

fun createEnvironment(envTitle: String, envKey: String, namespace: String, 
    branchName: String, envAccountId: String): Project {
    //val infraVCSRoot
}