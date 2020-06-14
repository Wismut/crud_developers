[![Build Status](https://travis-ci.org/Wismut/crud_developers.svg?branch=master)](https://travis-ci.org/Wismut/crud_developers)

##Task

Should implement a console CRUD application that interacts with the database and allows to perform all CRUD operations on entities:

* Developer (id, firstName, lastName, List<Skill> skills, Specialty specialty)
* Specialty (id, name, description)
* Skill (id, name)

 Requirements:
* All CRUD operations for each entity
* Stick to the MVC pattern (packages model, repository, service, controller, view)
* To build the project use Maven
* To migrate the database use https://www.liquibase.org/
* The application service layer should be covered with unit tests (junit + mockito).
* To import libraries use Maven
* To interact with the database - Hibernate
* To configure Hibernate - annotations
* Database initialization must be implemented using liquibase
* The result of the task should be a repository on github, using Travis (https://travis-ci.org/) and displaying the build status of the project.
 
 Technologies: Java, MySQL, JDBC, Maven, Liquibase, JUnit, Mockito, Hibernate
 
 Layers:

 * model - POJO classes
 * repository - classes that implement access to database
 * service - business logic
 * controller - processing requests from the user
 * view - all data necessary for working with the console

For example: Developer, DeveloperRepository, DeveloperController, DeveloperView, etc.

##Project

###Directory layout

     .
     ├── src
          ├── main
               ├── java
                    ├── command          # Actions with database
                    ├── controller       # Controllers layout
                    ├── factory          # DI container
                    ├── hibernate        # Hibernate utils
                    ├── liquibase        # Liquibase utils
                    ├── model            # Models layout
                    ├── repository       # Repositories layout
                         ├── impl        # Implementations of repositories within Hibernate
                         └── pool        # Connection pool implementation
                    ├── runner           # Class with main method
                    ├── service          # Service layout
                    └── view             # Views layout
                         └── impl        # Views implementations
          └── resources                  # Resources
               ├── changelog.yaml        # Liquibase changelog
               ├── hibernate.cfg.xml     # Hibernate configs
               └── logback.xml           # Log configs
     ├── .gitignore
     ├── .travis.yml
     ├── pom.xml
     └── README.md

###Repository

https://github.com/Wismut/crud_developers

###Requirements

* Installed Java 8
* Installed and running MySQL Server 8

###Building

1. ```git clone https://github.com/Wismut/crud_developers```
2. ```cd crud_developers```
3. ```mvn clean package```

###Running

1. ```java -jar target\crud.developers-1.0-SNAPSHOT-jar-with-dependencies.jar```
2. Follow the instructions in the console
