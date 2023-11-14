# Endpoints monitoring service

The Endpoints Monitoring Service is a Java microservice designed
to monitor HTTP/HTTPS URLs. It provides a REST API interface 
for users to perform CRUD (Create, Read, Update, Delete) operations on 
monitored endpoints, while also enabling automated background monitoring 
of URLs. The service logs information such as HTTP status codes 
and returned payloads.

## How to install and run the project

Follow these steps to get the project up and running on your local machine.

### Prerequisites

Before you start, ensure you have the following installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/installer/)

### Installation Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Dannnicek/monitoring_service

2. **Set Up the Database**

    Create a new MySQL database and note down the credentials (username, password, database name).
    Update the database configuration in src/main/resources/application.properties.

3. **Build and run the application**

    First build the application using
    ```bash
    mvn clean install
   ```
   Then run the application. It will start on http://localhost:8080.

## How to use the service

#### User Accounts

Upon starting the application, two user accounts will be created:

    User 1
        Name: Applifting
        Email: info@applifting.cz
        Access Token: 93f39e2f-80de-4033-99ee-249d92736a25
   
    User 2
        Name: Batman
        Email: batman@example.com
        Access Token: dcb20f8a-5657-4f1b-9f7f-ce65739b359e

### Managing Monitored Endpoints
#### Create a Monitored Endpoint
To create a new monitored endpoint, make a POST request to "/endpoints" with a JSON body in the following format:
```json
{
   "name": "example",
   "url": "http://example.com",
   "dateOfCreation": "2023-09-30T14:00:00",
   "dateOfLastCheck": "2023-09-30T14:00:00",
   "monitoredInterval": "monitoring interval in seconds",
   "owner": {
   "id": "1 for user applifting, 2 for user batman"
   }
}
```

#### Delete a Monitored Endpoint

To delete a monitored endpoint, make a DELETE request to "/endpoints/{name-of-endpoint}".

#### Listing Monitored Endpoints

To list all the endpoints for a user, make a GET request to "/endpoints". Authentication is required for this request. 
Include the access token in the request header in the form of a bearer token.

#### Viewing Monitoring Results

To view the results of the monitoring, make a GET request to "/results". Similar to listing endpoints, authentication is 
required. Include the access token in the request header as a bearer token. If you want to list only the results for a
specific endpoint, make a GET request to "/results/{name_of_endpoint}" and do not forget to include the access token.

## Testing
I have implemented some tests to test the proper functionality of my code. To run these tests, you can use the following
command:
```bash
    mvn test
   ```