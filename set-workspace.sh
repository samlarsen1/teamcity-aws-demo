#!/bin/bash

environments=(demo-dev demo-test)
environment=$1

if [ -z $1 ]; then
    echo "You must pass in an environment name"
else
    match=0
    for env in "${environments[@]}"; do
        if [[ $env = $environment ]]; then
            match=1
            break
        fi
    done
    if [[ $match = 0 ]]; then
        echo "Invalid environment set.  Possible values ${environments[*]}"
        exit 1
    fi
fi

printf "\nInitiating Terraform for workspace %s \n" $environment

terraform init -backend-config=config/$1.conf

printf "\nWriting workspace.auto.tfvars file"

printf 'remote_workspace="%s"' $environment > workspace.auto.tfvars