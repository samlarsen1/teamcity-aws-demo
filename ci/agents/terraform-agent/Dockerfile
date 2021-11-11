FROM jetbrains/teamcity-agent:2021.1-linux-sudo

# Switch to root to install terraform
USER root

RUN apt-get update &&\
    apt-get install -y gnupg software-properties-common curl

RUN curl -fsSL https://apt.releases.hashicorp.com/gpg | sudo apt-key add - &&\
    apt-add-repository "deb [arch=$(dpkg --print-architecture)] https://apt.releases.hashicorp.com $(lsb_release -cs) main" &&\
    apt install -y terraform

# Switch back to the buildagent user
USER buildagent

