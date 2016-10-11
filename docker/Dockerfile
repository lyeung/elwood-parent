FROM tomcat:8.0
MAINTAINER Lenming Yeung (lenming.yeung@gmail.com)

RUN apt-get update && apt-get install -y net-tools && apt-get install -y vim && apt-get install -y telnet && apt-get install -y openssh-server

RUN sh -c "echo root:root123 | chpasswd"

ADD sshd_config /etc/ssh/
RUN chmod u=rw,go=r /etc/ssh/sshd_config

RUN mkdir -p /opt/webapps
RUN mv /usr/local/tomcat/webapps/ROOT /usr/local/tomcat/webapps/root-bak
ADD ../elwood-web/target/*.war /usr/local/tomcat/webapps/ROOT.war

RUN echo "username:password is root:root123"