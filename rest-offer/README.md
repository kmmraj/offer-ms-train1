# rest-offer Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/rest-offer-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes
  with Swagger UI
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy
  Classic

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

## Changes

#### Ex-0:

- Created the working quarkus application

#### Ex-1:

- Now run the application in dev mode and curl the application 'curl http://localhost:8087/api/offers'
- Added the OpenAPI and Offer API with application parameters
- Visit http://localhost:8087/q/dev/
- Visit http://localhost:8087/q/swagger-ui/ to see the OpenAPI
- Change the documentation and show the changes

#### Ex-2: Tracing (Move it to after introducing the offer Price)

- install jaeger in docker

#####

`docker run -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268
jaegertracing/all-in-one:latest`

- Update the application properties to make it work
- View the changes in the URL http://localhost:16686/search

#### Ex-3: Security

- install keycloak in docker

#####

`docker run --name keycloak -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -p 8180:8080 jboss/keycloak`

- Open the URL http://localhost:8180/auth/ (admin/admin)
    - create a new realm called 'testrealm'
    - create a client called 'backend-service'
        - select client protocol 'openid-connect'
    - create 2 roles
        - admin
        - user
    - create 2 users (kadmin, kuser)
        - Make the flags on for
            - user verified
            - email verified
        - set the credentials
    - Assign user roles

- Update the application properties to make it work
   ```
  quarkus.oidc.auth-server-url=http://localhost:8180/auth/realms/testrealm
  quarkus.oidc.client-id=backend-service
  ```

- Add the pom dependency as follows
  ``` 
     <!-- https://mvnrepository.com/artifact/io.quarkus/quarkus-oidc -->
   <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-oidc</artifactId>
    </dependency>
  ```
- add a new API to test the user role

#### Ex-4: Security with client ID and client secret (Password Grant)

- Open the URL http://localhost:8180/auth/ (admin/admin)
    - create a new realm called 'cid-password-realm' (Password grant type) (Not recommended - Only for Native Apps)
    - create a client called 'backend-service'
        - select client protocol 'openid-connect'
            - make the "Access Type" as 'confidential'
            - make redirect url as 'http://localhost:8087' (Port of the dev)
            - make the "Authorization Enabled" as 'ON'
            - select tab 'credentials' and select the value 'client id and secret' for the dropdown 'client
              authenticator'
            - copy the value for the field 'secret'
            - and set that value in the application properties for the key 'quarkus.oidc.credentials.secret' (@refer#A )

            - Update the application properties to make it work
               ```
              quarkus.oidc.auth-server-url=http://localhost:8180/auth/realms/cid-realm
              quarkus.oidc.client-id=backend-service
              quarkus.oidc.credentials.secret=<value from the @refer#A >
              ```   

            - create 2 roles
                - admin
                - user
                - create 2 users (kadmin, kuser)
                    - Make the flags on for
                        - user verified
                        - email verified
                    - set the credentials
                - Assign user roles


- Add the pom dependency as follows,if not exists
  ``` 
     <!-- https://mvnrepository.com/artifact/io.quarkus/quarkus-oidc -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-oidc</artifactId>
    </dependency>
  ```
- add a new API to test the user role

#### Ex-5: Security with client ID and client secret (Client Credentials Grant)

- Open the URL http://localhost:8180/auth/ (admin/admin)
    - create a new realm called 'cid-ccgrant-realm'
    - create a client called 'backend-service'
        - select client protocol 'openid-connect'
            - make the "Access Type" as 'confidential'
            - make redirect url as 'http://localhost:8087' (Port of the dev)
            - make the "Service Accounts Enabled" as 'ON'
            - select tab 'credentials' and select the value 'client id and secret' for the dropdown 'client
              authenticator'
            - copy the value for the field 'secret'
            - and set that value in the application properties for the key 'quarkus.oidc.credentials.secret' (@refer#A )

            - Update the application properties to make it work
               ```
              quarkus.oidc.auth-server-url=http://localhost:8180/auth/realms/cid-ccgrant-realm
              quarkus.oidc.client-id=backend-service
              quarkus.oidc.credentials.secret=<value from the @refer#A >
              ```   


- Add the pom dependency as follows,if not exists
  ``` 
     <!-- https://mvnrepository.com/artifact/io.quarkus/quarkus-oidc -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-oidc</artifactId>
    </dependency>
  ```
- add a new API to test the user role

#### Ex-6: Security with API serving on public UI (Authorization code grant with PKCE)

- Open the URL http://localhost:8180/auth/ (admin/admin)
    - create a new realm called 'cid-authcode-pkce-grant-realm'
    - create a client called 'backend-service'
        - select client protocol 'openid-connect'
            - select Login Theme as 'keycloak'
            - make the "Access Type" as 'public'
            - make the standard flow as 'ON'
            - make redirect url as 'https://localhost:63342/ex3/rest-offer/index.html' (It is intelliJ idea built in web
              server)
            - Refer https://www.jetbrains.com/help/idea/php-built-in-web-server.html#configuring-built-in-web-server
            - Make Web Origins as "*"
            - Expand the Advanced Settings section.
                - For the Proof Key for Code Exchange Code Challenge Method option, select S256
            - Create user and assign roles
                - create 1 role
                    - user
                - create 1 user (kuser)
                    - Make the flags on for
                        - user verified
                        - email verified
                        - set the credentials
                        - Assign user roles
            - Update the application properties to make it work

```
              quarkus.oidc.auth-server-url=http://localhost:8180/auth/realms/cid-authcode-pkce-grant-realm
              quarkus.oidc.client-id=backend-service
              quarkus.http.auth.permission.authenticated.paths=/*
              quarkus.http.auth.permission.authenticated.policy=authenticated
 ``` 

[//]: # (TODO: Add scope by check - https://www.helikube.de/part-2-running-fine-grained-keycloak-authorization-feature-with-quarkus/)

- Add the pom dependency as follows, if not exists
  ``` 
     <!-- https://mvnrepository.com/artifact/io.quarkus/quarkus-oidc -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-oidc</artifactId>
    </dependency>
  ```
- Invoke the index.html in the browser (https://localhost:63342/ex3/rest-offer/index.html)

#### Ex-7: Offer getting the values from postgres database

- Install the postgres database
    - Using docker

          docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 arm64v8/postgres
          docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres

           docker exec -it postgres bash
           psql -U postgres
           create database offerdb;
           create user offeruser with encrypted password 'offeruser';
           grant all privileges on database offerdb to offeruser;
           \q
    - Install the postgres client
        - sudo apt-get install postgresql-client
        - https://dbeaver.io/
- Add the below to the pom.xml
  ```
    <!-- https://mvnrepository.com/artifact/io.quarkus/quarkus-jdbc-postgresql -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
  
    <!-- https://mvnrepository.com/artifact/io.quarkus/quarkus-hibernate-orm-panache -->
   <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm-panache</artifactId>
    </dependency>
  ```

- Add the below to the application.properties
  ```
    quarkus.datasource.db-kind = postgresql
    quarkus.datasource.username = postgres
    quarkus.datasource.password = mysecretpassword
    quarkus.datasource.jdbc.url = jdbc:postgresql://localhost:5432/offerdb
    
    # drop and create the database at startup (use `update` to only update the schema)
    quarkus.hibernate-orm.database.generation=drop-and-create
  
    %dev.quarkus.hibernate-orm.database.generation = drop-and-create
    %dev.quarkus.hibernate-orm.sql-load-script = insert_offer.sql
    
    %dev-with-data.quarkus.hibernate-orm.database.generation = update
    %dev-with-data.quarkus.hibernate-orm.sql-load-script = no-file
    
    %prod.quarkus.hibernate-orm.database.generation = none
    %prod.quarkus.hibernate-orm.sql-load-script = no-file
  ```
    - Modify the class Offer to extend PanacheEntity
    - Create a new class OfferRepository that extends PanacheRepository
    - Modify the OfferResource to use the OfferRepository

#### Ex-8: Deployment on Kubernetes

- Build it with the below command
  ```
  
  ./mvnw clean install -Dquarkus.container-image.builder=docker  
  ./mvnw clean package -Dquarkus.container-image.build=true
  ./mvnw clean package -Dquarkus.container-image.build=true -Dquarkus.container-image.push=true
  ./mvnw clean package -Dquarkus.container-image.build=true -Dquarkus.container-image.push=true -Dquarkus.container-image.registry=quay.io
  ./mvnw clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.push=true -Dquarkus.container-image.registry=quay.io
  ```
- Refer https://quarkus.io/guides/building-native-image
- Refer https://quarkus.io/guides/deploying-to-kubernetes
- Create a new namespace
  ```
  mvn clean package -Dquarkus.kubernetes.deploy=true
  ```

-- kubectl exec -it db-5b656447db-7fmpx -- /bin/bash

        kubectl exec -it db-5b656447db-7fmpx -- /bin/bash
        bash-5.1# psql -U postgres
        psql (14.1)
        Type "help" for help.
        
        postgres=# create database offerdb;
        CREATE DATABASE
        postgres=# create user offeruser with encrypted password 'offeruser';
        CREATE ROLE
        postgres=# grant all privileges on database offerdb to offeruser;
        GRANT

        postgres-# \c offerdb
        You are now connected to database "offerdb" as user "postgres".

        offerdb=# SELECT * FROM PUBLIC.OFFER;
        id    | cabinclass |    departuredate    | destination | flightid | origin
        ----------+------------+---------------------+-------------+----------+--------
        f602f151 |          0 | 2023-10-24 09:42:00 | MAD         | 500ba    | BCN
        f603f152 |          0 | 2023-10-24 11:42:00 | MAD         | 501ba    | BCN
        f604f153 |          0 | 2023-10-24 13:42:00 | MAD         | 502ba    | BCN
        (3 rows)

        postgres=# \q

- With docker image
- With docker compose
- Using kompose
- With kubernetes
- With persistent volume / persistent volume claim
- With secret

#### Ex-9: Save the secret

- Use k8s config maps
    - Using the kubectl command

           kubectl create configmap offer-api-config-map --from-literal=OFR_DBNAME=offerdb
    - Create the config-map.yaml
    - Apply the config map

           kubectl apply -f config-map.yaml
- Use k8s secrets
    - Using the kubectl command

           kubectl create secret generic offer-api-secret --from-literal=OFR_PASSWORD=mysecretpassword
    - Apply the secret

           kubectl apply -f secret.yaml
- Use vault secrets

[//]: # (  - TODO : to be explored - vault secrets)

#### Ex-10: Introduce the offer pricing and failover mode with circuit breaker and fallback

- Create a new microservice offer-price
  ```
  mvn io.quarkus.platform:quarkus-maven-plugin:2.13.2.Final:create \
  -DprojectGroupId=quarkus.mservices.offerprice \
  -DprojectArtifactId=rest-offer-price \
  -Dextensions='resteasy-reactive'
  cd rest-offer-price
  ```

  ```
- Update pom.xml
  ```
   <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <scope>provided</scope>
    </dependency>

    <!-- Hibernate ORM specific dependencies -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm</artifactId>
    </dependency>

    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm-panache</artifactId>
    </dependency>

    <!-- JDBC driver dependencies -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
  ```
- Create a new class OfferPrice that extends PanacheEntity
- Create a new class OfferPriceRepository that extends PanacheRepositoryBase
- Create a new class OfferPriceResource
- Create the endpoint to get the offer price by id


-
    - Add the below to the application.properties

```
  quarkus.http.port=8095
  %dev.quarkus.http.port=8097
  %test.quarkus.http.port=8099

  # datasource configuration
  quarkus.datasource.db-kind = postgresql
  quarkus.datasource.username = ${OFR_USERNAME:postgres}
  quarkus.datasource.password = ${OFR_PASSWORD:mysecretpassword}
  quarkus.datasource.jdbc.url = jdbc:postgresql://${OFR_HOSTNAME:localhost}:${OFR_PORT:5432}/${OFR_DBNAME:offerdb}
  # drop and create the database at startup (use `update` to only update the schema)
  quarkus.hibernate-orm.database.generation=drop-and-create
  
  %dev.quarkus.hibernate-orm.database.generation = drop-and-create
  %dev.quarkus.hibernate-orm.sql-load-script = insert_offer_price.sql
  
  %dev-with-data.quarkus.hibernate-orm.database.generation = update
  %dev-with-data.quarkus.hibernate-orm.sql-load-script = insert_offer_price.sql
  
  %prod.quarkus.hibernate-orm.database.generation = drop-and-create
  %prod.quarkus.hibernate-orm.sql-load-script = insert_offer_price.sql
  ```

- You should be able to call the offer price endpoint and get the offer price

- Now, lets update the offer to call offer price API and get the price details
    - Add the below to the pom.xml (rest-offer)

            ```
             <dependency>
              <groupId>io.quarkus</groupId>
              <artifactId>quarkus-rest-client</artifactId>
               </dependency> 
             ```
    - Create a new interface OfferPriceProxy
    - Inject the OfferPriceProxy in the OfferResource
         ```
          @Inject
          @RestClient
          OfferPriceProxy offerPriceProxy;
      ```
    - Create new DTO OfferExtendedDTO that includes the offer and the offer price
    - Change the return type of the endpoint to return the OfferExtendedDTO
    - For the every offer, call the offer price API and get the price details as below
       ```
              return offerList
                  .stream()
                  .map(offer -> Pair.create(offerPriceProxy.getOfferPrice(offer.getId()),offer))
                  .map(pair ->  getOfferExtendedDTO(pair.getLeft(), pair.getRight(),localDate))
                  .toList();
      ```
    - You should be able to call the offer endpoint and get the offer price details
    - Now introduce the following
        - Circuit breaker
        - Fallback
        - Retry
        - Timeout

#### Ex-11: Introduce gRPC

- Create a new microservice offer-grpc
  ```
  mvn io.quarkus.platform:quarkus-maven-plugin:2.13.2.Final:create \
  -DprojectGroupId=quarkus.mservices.offerprice \
  -DprojectArtifactId=rest-offer-price-grpc \
  -Dextensions='grpc'
  cd rest-offer-price-grpc
  ```
- Add the below to pom.xml

```
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-resteasy-reactive</artifactId>
    </dependency>
      <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.24</version>
      <scope>provided</scope>
    </dependency>

    <!-- Hibernate ORM specific dependencies -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm</artifactId>
    </dependency>

    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm-panache</artifactId>
    </dependency>

    <!-- JDBC driver dependencies -->
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
```

- Create a new proto file offerprice.proto
- Create a new class OfferPrice that extends PanacheEntity
- Create a new class OfferPriceRepository that extends PanacheRepositoryBase
- Create a new class OfferPriceService
- Create a new class OfferPriceEndpoint
- The endpoint should return the offer price by id (curl -X GET http://0.0.0.0:8097/api/offer-price/offer/f601f151)

#### Ex-12: Call the grpc service from the rest service

- Add the below to the pom.xml (rest-offer)

```
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-grpc-client</artifactId>
    </dependency>
```
- Remove the below to the pom.xml (rest-offer)

```
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-rest-client</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-resteasy-jsonb</artifactId>
    </dependency>
```


- Add the below to the application.properties (rest-offer)

```
 # Grpc Configurations
quarkus.grpc.clients.offerprice.host=localhost
quarkus.grpc.clients.offerprice.port=9010 # server port (A)
```

- In the OfferResource, inject the grpc client

```
   @GrpcClient("offerprice")
   OfferPriceServiceInterfaceGrpc.OfferPriceServiceInterfaceBlockingStub blockingOfferPriceService;
```
- Call the grpc service from the rest service in the stream map

```
   private OfferPriceResponse getOfferPriceResponse(String offerId) {
        return blockingOfferPriceService
                .getOfferPrice(
                        OfferPriceRequest
                                .newBuilder()
                                .setOfferId(offerId)
                                .build()
                );
    }
```


- you should change the grpc port to 9010 in the application.properties (rest-offer-price-grpc) as below

```
quarkus.grpc.server.port=9010 # server port (A)
quarkus.grpc.clients.offerprice.host=localhost
quarkus.grpc.clients.offerprice.port=9010
```
- You should be able to call the offer endpoint and get the offer price details

#### Ex-12: Tracing with Jaeger

#### Ex-13 Load Balancing and Service discovery

- Use stock quarkus
- Use Load Balancer
- Use Ingress
- Use Istio
    - Use Istio Ingress Gateway
    - Use Istio Virtual Service
    -

#### Ex-12: Helm and Pulumi

#### Ex-13: Monitoring with Prometheus and Grafana

#### Ex-14: Logging with ELK

#### Ex-15: API Gateway with Kong

#### Ex-16: API Gateway with Istio

#### Ex-17: Continuous Delivery with ArgoCD

#### Ex-18: Deploy the native image (jib)

#### Ex-19: Deploy the s2i image (openshift)