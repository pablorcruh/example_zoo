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

To Run tests.

```
    docker-compose up -d
    ./mvnw test
```


To build a docker image called example_zoo. Execute from the root
of the directory.
```
    docker build -t example_zoo .
```

## RUN THE PROJECT ON CONTAINERS
There is a folder called demo where you can find a docker-compose
file to startup the backend without running any other commands

Inside the demo folder
```
    docker-compose up -d
```

To stop everything
```
    docker-compose down
```

The tag on the image used has been tested 

## TEST ENDPOINTS
To test on localhost:

* GET http://localhost:8080/api/animals


* GET http://localhost:8080/api/animals/id

* POST http://localhost:8080/api/animals
```
    {
	"name":"animal 3",
	"category":"mammal"
    }
```
* DELETE http://localhost:8080/api/animals/id


* PUT http://localhost:8080/api/animals/id
```
   {
	"name":"anacondilla",
	"category":"reptile"
    }
```
