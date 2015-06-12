FROM phusion/baseimage:0.9.13
MAINTAINER Markus List <mlist@health.sdu.dk>
#adapted from https://raw.githubusercontent.com/mozart-analytics/grails-docker/master/Dockerfile

# Set customizable env vars defaults.
ENV GRAILS_VERSION 2.2.5

# Set phusion/baseimage's correct settings.
ENV HOME /root

# Disable phusion/baseimage's ssh server (not necessary for this type of container).
RUN rm -rf /etc/service/sshd /etc/my_init.d/00_regen_ssh_host_keys.sh

# Download Install Java
RUN apt-get update
RUN apt-get install -y openjdk-7-jdk unzip wget

# Setup Java Paths
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Install Grails
WORKDIR /usr/lib/jvm
RUN wget http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-$GRAILS_VERSION.zip
RUN unzip grails-$GRAILS_VERSION.zip
RUN rm -rf grails-$GRAILS_VERSION.zip
RUN ln -s grails-$GRAILS_VERSION grails

# Setup Grails path.
ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH

# Create App Directory
WORKDIR /
RUN mkdir /app
COPY . /app
WORKDIR /app

# Compile grails app
RUN grails refresh-dependencies
RUN grails compile

# Clean up APT.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Set Default Behavior
# Use phusion/baseimage's init system.
# See http://phusion.github.io/baseimage-docker/ for more info.
ENTRYPOINT ["/sbin/my_init", "grails"]
CMD ["prod", "run-war"]
