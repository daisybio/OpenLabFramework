FROM mlist/grails:2.5.0
MAINTAINER Markus List <mlist@mpi-inf.mpg.de>

# Create App Directory
COPY . /app
WORKDIR /app

# Setup Grails paths
ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH

# Setup Java paths
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Compile grails app
RUN grails refresh-dependencies; grails compile

# Start grails app
ENTRYPOINT ["/sbin/my_init", "grails"]
CMD ["prod", "run-war"]
