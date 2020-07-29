# This Docker file builds a basic minecraft server
# directly from the default minecraft server from Mojang
#
FROM ubuntu:20.04
MAINTAINER Kyle Brown “brownkyl@us.ibm.com”
RUN apt-get update
RUN apt-get install -y default-jdk
RUN apt-get install -y wget
RUN mkdir minecraft
RUN wget -O minecraft/server.jar \ 
https://launcher.mojang.com/v1/objects/a412fd69db1f81db3f511c1463fd304675244077/server.jar
RUN echo "eula=true" > eula.txt
CMD java -Xms512m -Xmx1024m -jar minecraft/server.jar nogui
EXPOSE 25565
