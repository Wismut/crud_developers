[![Build Status](https://travis-ci.org/Wismut/crud_developers.svg?branch=master)](https://travis-ci.org/Wismut/crud_developers)

##Task

Should implement a REST API application that interacts with the database and allows to perform all CRUD operations on entities:

* User (id, username, password, created, updated, lastPasswordChangeDate, Status status, String phoneNumber)
* Developer (id, firstName, lastName, Set<Skill> skills, Specialty specialty, Account account)
* Specialty (id, name, description)
* Skill (id, name)
* Account (id, accountData)
Enum Status (ACTIVE, APPROVAL_REQUIRED, DELETED, BANNED)

 Requirements:
* All CRUD operations for each entity
* Stick to the MVC pattern (packages model, repository, service, controller, view)
* To build the project use Maven
* Use Spring(IoC, Security, Data) as a main framework
* To migrate the database use https://www.liquibase.org/
* The application service and controller layers should be covered with unit tests (JUnit + Mockito)
* To import libraries use Maven
* To interact with the database - Spring Data
* Database initialization must be implemented using liquibase
* Application must have 3 roles:
  1. ROLE_ADMIN (has full access for all entities)
  2. ROLE_MODERATOR (has read access to all entities and write access to Developer and Account entities)
  3. ROLE_USER (has read access for Developer, Account and Skill entities)
* Application must be deployed to https://heroku.com
* Registration must be approved by phone number. Use https://www.twilio.com
* Repository  must have Travis (https://travis-ci.org/) badge with status of deploy.

 Technologies: Java, MySQL, Maven, Liquibase, JUnit, Mockito, Spring(IoC, Security, Data)

 Layers:

 * model - POJO classes
 * repository - classes that implement access to database
 * service - business logic
 * controller - processing requests from the user
 * security - classes for access dividing

##Project

###Directory layout

     .
     ├── src
          ├── main
               ├── java
                    ├── config           # Spring configuration
                    ├── controller       # Controllers layout
                    ├── dto              # Dto layout
                    ├── exception        # Controller exceptions handlers
                    ├── model            # Models layout
                    ├── repository       # Repositories layout
                    ├── security         # Jwt token logic
                    ├── service          # Service layout
                    └── validator        # Validators for entities
          └── resources                  # Resources
               ├── dbchangelog.yaml      # Liquibase changelog
               ├── logback.xml           # Log configs
               └── populateDB.sql        # SQL scripts for database populating
     ├── .gitignore
     ├── .travis.yml
     ├── pom.xml
     ├── Procfile
     └── README.md

###Repository

https://github.com/Wismut/crud_developers

###Running

Visit http://cruddevelopers.herokuapp.com/swagger-ui.html for full info about each API in project.
First of all, must make POST request to http://cruddevelopers.herokuapp.com/api/v1/auth/login with proper credentials with body
```{"username":"AAA", "password":"BBB"}```.
Response will have token. Must add token to all next requests in the Authorization header.
For example, Bearer_XXXXX where XXXXX is the token received earlier.

###Roles
Login/Password:

* user/test
* moderator/test
* admin/test
