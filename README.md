# zkp-service

This is a service that tries to utilize the [zero knowledge proof](https://en.wikipedia.org/wiki/Zero-knowledge_proof) concept to authenticate a client/user.
The service hosts apis to do certain things:
- Register a new user (email and hash of their password)
- Login user and send back a challenge to solve
- The challenges are solved and on success, the user is granted access to the secure resource
- The service does not (at any time) processes the users plain text password
- The service could be enhanced to present random challenges that could make this whole authentication flow more robust

<b>TODO</b>

- This is just one of the challenges that could be used for the authentication process, more and random challenges need to be thought of and included
- The password hash rotate mechanism for resetting it by the user and also by the service in case they are leaked

## Running the application
Relies on a postgres database, you can start one by the following command:
```shell
docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```
Post this, just run the [main](https://github.com/hack-2023/zkp-service/blob/main/src/main/java/com/hack2023/zkp_service/Application.java#L15) method in Application class.


## Sample sequence diagram

![sample-sequence](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/hack-2023/zkp-service/main/sample-sequence.iuml)
