# spring-boot-app
Spring boot sample application

Read this file to understand how to setup the application on local and execute curl command to call the endpoints.

# A. Steps to setup the application and run maven build.

  1. Import the code on local drive.
  2. Open cmd prompt on local and cd to the path of directory where code is imported.
  3. Run the command **mvn clean install** to build the application and run the tests.
  4. Once the build finishes successfully run the command **mvn spring-boot:run** to start the application          
  5. Once the server starts successfully the application should be up and running on port 9090 (default configured port in               application.properties)

# B. Below are curl commands to call different endpoints
 
# Curl command to call 

  1.  Greeting endpoint:
  
        curl --verbose http://localhost:9090/api/rest/greeting

  2. external REST webservice endpoint:

        curl --verbose -H "Accept:application/json" http://localhost:9090/api/rest/jsonPlaceHolder/posts

  3. find word count endpoint: 

        curl --verbose -H "Accept: application/json" -G -X GET http://localhost:9090/api/rest/paragraph/wordCount --data-urlencode "paragraph=A-   word1 D-word2 C-word3 C-word3 D-word4     G-word5 1-word1 ##$$%% ##$$%%"

  4. create thread endpoint:

        curl --verbose -H "Accept: application/json" -X POST http://localhost:9090/api/rest/threads/create

  5. check thread deadlock status endpoint:

        curl --verbose -G http://localhost:9090/api/rest/threads/deadlockStatus --data-urlencode {firstThreadName} --data-urlencode {secondThreadName}

        **Note: Please use firstThreadName and secondThreadName from the location response header of above endpoint(/threads/create) .

  6.  create employee record endpoint:

        curl --verbose -H "Content-Type: application/json"  "Accept: application/json" -d '{"name":"Name 1", "salary":2000}' -X POST http://localhost:9090/api/rest/employees

  7.  find employee record endpoint:

        curl --verbose -H "Accept: application/json" -X GET http://localhost:9090/api/rest/employees/{id}

  8.  find all employee record endpoint:

        curl --verbose -H "Accept: application/json" -X GET http://localhost:9090/api/rest/employees

  9.  delete employee record endpoint

        curl --verbose -X DELETE http://localhost:9090/api/rest/employees/{id}
