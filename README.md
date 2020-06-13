[![Build Status](https://travis-ci.org/Wismut/crud_developers.svg?branch=master)](https://travis-ci.org/Wismut/crud_developers)

##Task

Should implement a console CRUD application that interacts with the database and allows to perform all CRUD operations on entities:

* Developer (id, firstName, lastName, List<Skill> skills, Specialty specialty)
* Specialty (id, name, description)
* Skill (id, name)

 Requirements:
* Stick to the MVC pattern (packages model, repository, service, controller, view)
* To migrate the database use https://www.liquibase.org/
* The application service layer should be covered with unit tests (junit + mockito).
* To import libraries use Maven
* The result of the project should be a separate repository on github, with a description of the task, project and instructions for launching the project locally.

 Technologies: Java, MySQL, JDBC, Maven, Liquibase, JUnit, Mockito
 
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
                    ├── model            # Models layout
                    ├── repository       # Repositories layout
                         ├── csv         # CSV implementations of repositories
                         └── io          # IO implementations of repositories
                    ├── runner           # Class with main method
                    └── view             # Views layout
                         └── impl        # Views implementations
          ├── resources                  # Resources
               └── files                 # Data warehouse
                    ├── csv              # CSV data warehouses
                    └── io               # TXT data warehouses
     ├── .gitignore
     └── README.md

###Repository

https://github.com/Wismut/crud_developers

###Compiling

1. ```git clone https://github.com/Wismut/crud_developers```
2. ```cd crud_developers```
3. for Windows 
```dir /s /B *.java > sources.txt```

   for Linux/MacOS 
```find -name "*.java" > sources.txt```
4. ```javac @sources.txt```

###Running

1. ```java -cp src\main\java\ runner.Runner```
2. Follow the instructions in the console
