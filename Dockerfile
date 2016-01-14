FROM mlist/grails:2.5.0
MAINTAINER Markus List <mlist@mpi-inf.mpg.de>

# Create App Directory
COPY . /app
WORKDIR /app

# Compile grails app
RUN grails refresh-dependencies; grails compile

# Start grails app
ENTRYPOINT ["/sbin/my_init", "grails"]
CMD ["prod", "run-war"]
