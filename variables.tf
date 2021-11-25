variable "remote_workspace" {
    description = "Name of remote workspace to use. Options outlined in vars module"
    type = string
}

variable "instance_name" {
    description = "Value of name tag for EC2 variable"
    type = string
    default = "ExampleAppServerInstance"
}

