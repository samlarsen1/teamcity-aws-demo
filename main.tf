terraform {
  backend "remote" { }
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }

  required_version = ">= 0.14.9"
}

provider "aws" {
  region  = "us-west-2"
}

module "vars" {
  source = "./modules/vars"
  environment = var.remote_workspace
}
module "vpc" {
  source = "terraform-aws-modules/vpc/aws"

  name = "${module.vars.env.name}-vpc"
  cidr = "10.0.0.0/16"

  azs             = module.vars.env.availability_zones
  private_subnets = module.vars.env.private_subnets
  public_subnets  = module.vars.env.public_subnets

  enable_nat_gateway = true
  enable_vpn_gateway = true

  tags = {
    Terraform = "true"
    Environment = module.vars.env.name
  }
}

module "ec2_instance" {
  source  = "terraform-aws-modules/ec2-instance/aws"
  version = "~> 3.0"
  
  for_each = toset(["one","two"])

  name = "instance-${each.key}"

  ami                    = "ami-ebd02392"
  instance_type          = module.vars.env.instance_type
  key_name               = "user1"
  monitoring             = true
  subnet_id              = module.vpc.private_subnets[0]

  tags = {
    Terraform   = "true"
    Environment = module.vars.env.name
  }
}