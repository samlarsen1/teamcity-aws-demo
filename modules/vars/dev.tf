locals {
    demo-dev = {
        name = "demo-dev"
        availability_zones = ["eu-west-1a", "eu-west-1b"]
        private_subnets = ["10.0.1.0/24", "10.0.2.0/24"]
        public_subnets  = ["10.0.101.0/24", "10.0.102.0/24"]
        instance_type = "t2.micro"
    }
}