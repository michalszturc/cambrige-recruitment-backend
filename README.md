# Cambrige recruitment backend


## Getting started
Application using [Java 17](https://www.azul.com/downloads/?package=jdk#download-openjdk
) and [Gradle 7.2.](https://docs.docker.com/compose/)

To build local docker image run gradlew scripts with the following command (on UNIX machine):

````shell
$ ./gradlew bI
````
Application is running inside [docker](https://www.docker.com/) container.

Application is started
using [docker-compose](https://docs.docker.com/compose/) tool.

Runtime configuration is injected via environment variables, which are defined in [`.env` file](https://docs.docker.com/compose/env-file/)
Sample content is provided in `.env.template` file.


To run enter the following command:

````shell
$ docker-compose up -d
````