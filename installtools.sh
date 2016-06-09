wget -qO- https://get.docker.com/ | sed 's/lxc-docker/lxc-docker-1.11.1/' | sh
sudo usermod -aG docker kylebrown
wget "https://cli.run.pivotal.io/stable?release=debian64&version=6.18.1&source=github-rel" -O cf-cli_amd64.deb
sudo dpkg -i cf-cli_amd64.deb
cf install-plugin https://plugins.ng.bluemix.net/downloads/cf-plugins/ibm-containers/ibm-containers-linux_x64
sudo service docker restart


