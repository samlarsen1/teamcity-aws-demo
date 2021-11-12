

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule


version = "2021.2"
project {
    description = "Terraform Pipeline"

    buildType(Validate)
    buildType(ApplyDev)
    buildType(ApplyTest)
    buildType(Destroy)

    sequential {
        buildType(Validate)
        buildType(ApplyDev)
        buildType(ApplyTest)
    }

}

object Validate : BuildType({
    name = "Validate"
    description = "Test terraform code"

    steps {
        //  TODO: Add terraform validation checks here
        script {
            name = "Check OS verions"
            scriptContent = """
            lsb_release -a
            whoami
            """.trimIndent()
        }
        script {
            name = "Check terraform installed"
            scriptContent = """
            $(dpkg-query -W -f='${'$'}{Status}' terraform 2>/dev/null | grep -c "ok installed")
            """.trimIndent()
        }
    }
})

object ApplyDev : BuildType({
    name = "ApplyDev"
    description = "Apply terraform code"
    vcs {
        root(DslContext.settingsRoot)
    }
    dependencies {
        snapshot(Validate){}
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
          name = "Terraform Plan"
          //  TODO: Add workspace config in here for environment
          scriptContent = """
          terraform init
          terraform plan 
          """.trimIndent()
      }
        script {
            name = "Terraform Apply"
            //  TODO: Add workspace config in here for environment
            scriptContent = """
          terraform apply
          """.trimIndent()
        }
    }
})

object ApplyTest : BuildType({
    name = "ApplyTest"
    description = "Deploy to Test environment"

    dependencies {
        snapshot(ApplyDev){}
    }

    steps {

    }
})

object Destroy : BuildType({
    name = "Destroy"
    description = "Destroy environment"

    triggers {
        schedule {
            schedulingPolicy = cron {
                "0 0 18 1/1 * ? *"
            }
        }
    }
    steps {
        //  TODO: Add terraform destroy here
    }
})
