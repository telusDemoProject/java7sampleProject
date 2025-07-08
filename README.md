# Spring Boot Java 11 Project

**Java Version:** 11  
**Spring Boot Version:** 2.7.18  
**Maven Version:** 3.9.9  
**Testing Framework:** JUnit 5

## Java 11 Features Used
- `var` keyword for local variable type inference
- Stream API with modern collectors
- Switch expressions
- Files API for file operations
- Modern JWT library (0.11.x)
- Enhanced validation annotations

## Commands to run locally:

```bash
mvn clean install
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run integration tests
mvn test -Dtest=ApplicationIntegrationTest
```


✅ 1. Create a User
bash
CopyEdit
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Rama","email":"rama@example.com"}'

✅ 2. Get All Users
bash
CopyEdit
curl http://localhost:8080/users

✅ 3. Get User by ID
bash
CopyEdit
curl http://localhost:8080/users/1

✅ 4. Update User by ID
bash
CopyEdit
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated Rama","email":"updated@example.com"}'

✅ 5. Delete User by ID
bash
CopyEdit
curl -X DELETE http://localhost:8080/users/1

✅ 6. Search Users by Name (Optional Filter Logic if Added)
bash
CopyEdit
curl http://localhost:8080/users/search?keyword=Rama
(Assumes you add a custom filtering endpoint later, optional)




File Operations to create , read and delete a file

# Create a file
curl -X POST "http://localhost:8080/file/create?filename=test.txt&content=HelloWorld"

# Read the file
curl "http://localhost:8080/file/read?filename=test.txt"

# Delete the file
curl -X DELETE "http://localhost:8080/file/delete?filename=test.txt"

