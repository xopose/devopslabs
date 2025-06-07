terraform {
  required_providers {
    twc = {
      source = "tf.timeweb.cloud/timeweb-cloud/timeweb-cloud"
    }
  }
  required_version = ">= 0.13"
}

data "twc_configurator" "configurator" {
  location = "ru-1"
  disk_type = "nvme"
}

data "twc_os" "os" {
  name = "ubuntu"
  version = "22.04"
}
# Create new SSH key
resource "twc_ssh_key" "ssh-key" {
  name = "LazerKey"
  body = file("~/.ssh/id_ed25519.pub")
}

resource "twc_server" "devops-server" {
  name = "devops server"
  os_id = data.twc_os.os.id


  configuration {
    configurator_id = data.twc_configurator.configurator.id
    disk = 1024 * 15
    cpu = 1
    ram = 1024
  }
  ssh_keys_ids = [twc_ssh_key.ssh-key.id]
}
resource "twc_server_ip" "example" {
  source_server_id = twc_server.devops-server.id

  type = "ipv4"
}

provider "twc" {
  token = "YOU_TOKEN"
}