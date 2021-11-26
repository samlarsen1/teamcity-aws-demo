# TeamCity Terraform Kotlin DSL Test

This repo is for testing automated terraform deployments using TeamCity Kotlin DSL,
 Terraform Enterprise (or Cloud) and AWS.
 
## Prerequisites

### Local
* Terraform

### Remote
* Terraform Enterprise 
* AWS

## Running

### Terraform Enterprise Setup

Create a user or team API key for Terraform Enterprise (or Cloud) and store as described below.

Create a different workspace for each environment.

In each workspace create AWS account credentials and store them as environment variables
![Terraform Enterprise Environment Variables](resources/tf-variables.png)

### Local

Create local TF Enterprise token authentication config in `~/.terraformrc`

```hcl-terraform
credentials "app.terraform.io" {
  token = "AAAA.BBBBB.CCCCC"
  # this being a team or user token (not an organisation token)
}
```

Run terraform plan
```shell script
# setup remote workspace (includes terraform init)
./set-workspace.sh demo-dev

terraform plan
```

### CI

Create a TF Enterprise token and add as an environment variable in CI called xxxxxx.

Use a Terraform capable build agent with TeamCity

CI (kotlin) will pick up this variable and use it to authenticate.
