# Example Zoo

This is an example to manage animals from a Zoo.

The project uses Test Driven Development to write the code 
on the application.

The project also uses Integration Test as part of the project
so we can assure every feature works as expected.

We can manage animal by creating, updating, deleting and searching
for an animal using the http verbs POST, PUT, DELETE and GET.

The Project has a docker-compose file with a database preconfigured to be
used as backend for tests.

The Project is using **Github** **Actions** to run tests and build and
push a container to docker hub.

[Docker Repository for example_zoo](https://hub.docker.com/repository/docker/pablorcruh/example_zoo)

Check the docker image repository to view tags availables.

To build the project.

```
    docker-compose up -d
    ./mvnw clean install
```

To build a docker image called example_zoo. Execute from the root
of the directory.
```
    docker build -t example_zoo .
```

