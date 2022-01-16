# POC - Blocking Queue
Purpose: understand the concepts and simulations an API with a limit calls

## Running
- Start wiremock through docker-compose:
```
docker-compose up
```
- Start the application 
```
./gradlew bootRun
```
- Invoke the endpoint passing the queue capacity (e.g: 10). it's going to create 200 elements to be processed.
```
curl --location --request GET 'http://localhost:8080/execute?queueCapacity=10'
```
