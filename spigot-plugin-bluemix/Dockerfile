# Version 0.0.4
# This version builds a spigot server
# using the recommended build strategy for spigot
# This is advantageous in that it’s better for plugin development
# and fits well with the Docker approach
# it also adds a first Minecraft plugin into the bare spigot server
#
FROM ubuntu:16.04
MAINTAINER Kyle Brown “brownkyl@us.ibm.com”
RUN apt-get update &&\
	apt-get install -y git &&\
	apt-get install -y default-jdk &&\
	apt-get install -y wget &&\
	mkdir minecraft &&\
	wget "https://hub.spigotmc.org//jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar" -O minecraft/BuildTools.jar &&\
	git config --global core.autocrlf input &&\
	java -jar minecraft/BuildTools.jar --rev 1.12 &&\
	rm -r Bukkit &&\
	rm -r CraftBukkit &&\
	rm -r Spigot &&\
	rm -r BuildData &&\
	rm -r apache-maven-3.2.5 &&\
	rm -r work &&\
	rm craftbukkit-1.12.jar &&\
	rm -r minecraft &&\
	apt-get purge -y --autoremove git wget
RUN echo "eula=true" > eula.txt &&\
	mkdir plugins
ADD Tutorial.jar /plugins/Tutorial.jar
CMD java -Xms512m -Xmx1024m -jar spigot-1.12.jar nogui
EXPOSE 25565
