# Version 0.0.3
# This version builds a spigot server
# using the recommended build strategy for spigot
# This is advantageous in that it’s better for plugin development
# and fits well with the Docker approach
#
FROM ubuntu:20.04
MAINTAINER Kyle Brown “brownkyl@us.ibm.com”
RUN apt-get update -y 
RUN apt-get install -y git
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y default-jdk
RUN apt-get install -y wget
RUN mkdir minecraft
RUN wget "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar" -O minecraft/BuildTools.jar
RUN git config --global core.autocrlf input
RUN java -jar minecraft/BuildTools.jar --rev 1.16.1
RUN echo "eula=true" > eula.txt
CMD java -XX:MaxPermSize=128M -Xms512m -Xmx1024m -jar spigot-1.16.1.jar nogui
EXPOSE 25565
