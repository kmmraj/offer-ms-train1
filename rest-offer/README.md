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

  - Warm up the DB
  

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

          postgres-# \c offerdb;
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



#### Ex-13 Load Balancing and Service discovery

- Install Istio

    ```
    curl -L https://istio.io/downloadIstio | ISTIO_VERSION=1.16.0 TARGET_ARCH=x86_64 sh -
    cd istio-1.16.0/
    export PATH=$PWD/bin:$PATH
    echo $PATH
    istioctl install --set profile=demo -y
    ```
  
- Verify the installation

    ```
    kubectl get pods -n istio-system
    ```
- Enable Istio

  ```
  kubectl label namespace istio-system istio-injection=enabled
  ```

- Note : In macOS machine, apply the below in the terminal where the mvn command is executed

    ```
    export DOCKER_DEFAULT_PLATFORM=linux/amd64 
    ```
- Build the docker images

    ```
   ./mvnw clean install -Dquarkus.container-image.builder=docker
    ```
- Deploy the docker images

  - Deploy the offer-api application in cloud
    - src/main/k8s/01-config-map.yaml
    - src/main/k8s/02-secret.yaml
    - src/main/k8s/03A-pg-database-dv-pvc.yaml
    - src/main/k8s/03B-db-deployment.yaml
    - src/main/k8s/04-kubernetes.yaml
    - src/main/k8s/05-create-http-gateway.yaml
    - src/main/k8s/06-create-virtual-service.yaml
     
    ```
       kubectl apply -f 01-config-map.yaml
       kubectl apply -f 02-secret.yaml
       kubectl apply -f 03A-pg-database-dv-pvc.yaml
       kubectl apply -f 03B-db-deployment.yaml 
    ```
  - Warm up the DB

     ```
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

          postgres-# \c offerdb;
          You are now connected to database "offerdb" as user "postgres".

          offerdb=# SELECT * FROM PUBLIC.OFFER;
          id    | cabinclass |    departuredate    | destination | flightid | origin
          ----------+------------+---------------------+-------------+----------+--------
          f602f151 |          0 | 2023-10-24 09:42:00 | MAD         | 500ba    | BCN
          f603f152 |          0 | 2023-10-24 11:42:00 | MAD         | 501ba    | BCN
          f604f153 |          0 | 2023-10-24 13:42:00 | MAD         | 502ba    | BCN
          (3 rows)

          postgres=# \q
     ```
  
  - Verify the Istio Configurations
   ```
      istioctl analyze -n=istio-system
      
    ```
 
    - Deploy the offer-price-api application in cloud
     
     ```
     kubectl apply -f 04-kubernetes.yml
     ```
  -  Enable the istio gateway and virtual service
     ```
     kubectl apply -f 05-create-http-gateway.yaml
     kubectl apply -f 06-create-virtual-service.yaml
     ```

  - Access the application using the Istio Ingress Gateway

   ```
   curl -X GET http://<istio-ingress-gateway-ip>/api/offers/orig/BCN/dest/MAD/date/2023-05-05
   curl -X GET http://34.135.182.242/api/offers/orig/BCN/dest/MAD/date/2023-05-05
   ```

#### Ex-14: More on Istio Load Balancing and Service discovery

- Note : In macOS machine, apply the below in the terminal where the mvn command is executed

    ```
    export DOCKER_DEFAULT_PLATFORM=linux/amd64 
    ```

- Update the offer price app to serve the tax (This is the V2 change - New Feature :-)
- Update proto file
- Update related classes
  ```
  rest-offer-price-grpc/src/main/java/quarkus/mservices/offerprice/OfferPriceService.java
  rest-offer-price-grpc/src/main/java/quarkus/mservices/offerprice/repository/OfferPrice.java
  rest-offer-price-grpc/src/main/proto/offerprice.proto
  rest-offer-price-grpc/src/main/resources/insert_offer_price.sql
  rest-offer/src/main/java/quarkus/mservices/offer/OfferExtendedDTO.java
  rest-offer/src/main/java/quarkus/mservices/offer/OfferResource.java
  rest-offer/src/main/proto/offerprice.proto
  ```
- Build the offer-price-api application
  ```
   ./mvnw -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true clean install -Dquarkus.container-image.builder=docker
  ```
- Update the DB, create the new column tax
    ```
    kubectl exec -it db-5b656447db-7fmpx -- /bin/bash
    bash-5.1# psql -U postgres -d offerdb
    psql (14.1)
    Type "help" for help.
    
    postgres=# \c offerdb
    You are now connected to database "offerdb" as user "postgres".
    
    offerdb=# ALTER TABLE offerprice ADD COLUMN tax numeric(10,2);
    ALTER TABLE
   offerdb=# SELECT * FROM PUBLIC.OFFERPRICE;
    id    | currency | offerid  | price  |  tax
   ----------+----------+----------+--------+-------
    g601f151 | EUR      | f601f151 |  70.00 |  
    g602f152 | EUR      | f602f152 | 100.00 | 
    g603f153 | EUR      | f603f153 | 120.00 | 
    g604f154 | EUR      | f604f154 | 150.00 | 
    
    (4 rows)
  
    UPDATE offerprice SET tax = 7.00 WHERE id = 'g601f151';
    UPDATE offerprice SET tax = 10.00 WHERE id = 'g602f152';
    UPDATE offerprice SET tax = 12.00 WHERE id = 'g603f153';
    UPDATE offerprice SET tax = 15.00 WHERE id = 'g604f154';
    commit;
  
    offerdb=# SELECT * FROM PUBLIC.OFFERPRICE;
    id    | currency | offerid  | price  |  tax
    ----------+----------+----------+--------+-------
    g601f151 | EUR      | f601f151 |  70.00 |  7.00
    g602f152 | EUR      | f602f152 | 100.00 | 10.00
    g603f153 | EUR      | f603f153 | 120.00 | 12.00
    g604f154 | EUR      | f604f154 | 150.00 | 15.00
    (4 rows)

    
    offerdb=# \q
    ```

- Update the kubernetes files to serve the V2 of the offer price app 
  ```
  rest-offer-price-grpc/src/main/k8s/04-kubernetes.yaml
  rest-offer/src/main/k8s/04-kubernetes.yaml
  ```
- Create virtual service with subsets, weight and traffic policy 
   to route the traffic to the V2 of the offer price app
    ```
   rest-offer-price-grpc/src/main/k8s/02-virtual-service.yaml
   ```
  
- Create the destination rule to define the subsets
    ```
   rest-offer-price-grpc/src/main/k8s/03-destination-rule.yaml
   ```
- Access the application using the Istio Ingress Gateway

   ```shell
   curl -X GET http://<istio-ingress-gateway-ip>/api/offers/orig/BCN/dest/MAD/date/2023-05-05
  ```
  - Change the weight of the V2 to 80% and access the application using the Istio Ingress Gateway
    ```
    rest-offer-price-grpc/src/main/k8s/04-virtual-service.yaml
    ```
    ```yaml
      - destination:
          host: offer-price-api
          subset: v1
        weight: 20
      - destination:
            host: offer-price-api
            subset: v2
        weight: 80
    ```
   - Apply the changes
    ```
    kubeclt apply -f rest-offer-price-grpc/src/main/k8s/04-virtual-service.yaml
    ```
   - Access the application using the Istio Ingress Gateway
  
     ```
     curl -X GET http://<istio-ingress-gateway-ip>/api/offers/orig/BCN/dest/MAD/date/2023-05-05
     curl -X GET http://
     ```
#### Ex-15: Tracing with Jaeger and Kiali

- Install Jaeger and Kiali
    ```shell
      kmmraj@cloudshell:~$ cd istio-1.16.0/
      kmmraj@cloudshell:~/istio-1.16.0$ kubectl apply -f samples/addons
   ```
- Access the Kiali Dashboard UI
     ```shell
      istioctl dashboard kiali
    ```
  - Access the Jaeger Dashboard UI
     ```shell
      istioctl dashboard jaeger
     ```
  
- You can also install the Kiali and Jaeger components in the Istio namespace individually like below
  ```shell
  kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.16/samples/addons/jaeger.yaml
  kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.16/samples/addons/kiali.yaml
  ```


#### Ex-16: Monitoring with Prometheus and Grafana
- Install Prometheus and Grafana
    ```
      kmmraj@cloudshell:~$ cd istio-1.16.0/
      kmmraj@cloudshell:~/istio-1.16.0$ kubectl apply -f samples/addons
     ```
- Access the Grafana Dashboard UI
     ```
      istioctl dashboard grafana
  ```
- Access the Prometheus Dashboard UI
  ```
  istioctl dashboard prometheus
  ```
- You can also install the Prometheus and Grafana components in the Istio namespace individually like below
  
  ```shell
    kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.16/samples/addons/prometheus.yaml
    kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.16/samples/addons/grafana.yaml
    ```

#### Ex-17: Logging with ELK

 -  Run the application from local dev machine and see the logs in Kibana 

 - #### Deploy the ELK stack in the docker desktop 
   - ELK stack docker compose file is available in the repo docker-elk-custom/docker-compose.yml
      
     ```shell
        docker-compose -f docker-compose.yml -f extensions/filebeat/filebeat-compose.yml up -d
      ```
      - Access the Kibana Dashboard UI (username: elastic, password: changeme)
        ```url
          http://localhost:5601
        ```
      - Note: If you need to shut down ELK stack
          
         ```shell
          docker-compose down -v
          ```
   
 - #### configuration changes - rest-offer-price-grpc 
   
   - 
      - in rest-offer-price-grpc/src/main/resources/application.properties
        ```properties
        quarkus.log.handler.gelf.enabled=true
        quarkus.log.handler.gelf.host=localhost
        quarkus.log.handler.gelf.port=50000
        ```
    - Add the following dependency in rest-offer-price-grpc/pom.xml
      ```xml
      <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-gelf</artifactId>
      </dependency>
      ```
    - Run the application from local dev machine
      ```shell
      quarkus dev -Ddebug=5009
      ```
        
 - #### Configuration changes (rest-offer)
   - in rest-offer/src/main/resources/application.properties add the below
   
    ```properties 
        quarkus.log.handler.gelf.enabled=true
        quarkus.log.handler.gelf.host=localhost
        quarkus.log.handler.gelf.port=50000
    ```
 
- Add dependencies in pom.xml
  ```xml
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-logging-gelf</artifactId>
    </dependency>
  ```
- Run the application as below

  ```shell
   quarkus dev -Dquarkus.container-image.builder=docker  -Ddebug=5007
  ```
  
- #### Access the Kibana Dashboard UI (username: elastic, password: changeme)
  ```url
    http://localhost:5601
  ```
  - Navigate to the Discover tab, you can see the logs from the application

#### Ex-18: Apache Kafka using MicroProfile Reactive Messaging

## Goal
- To demonstrate the use of MicroProfile Reactive Messaging to send and receive messages from Apache Kafka

## Steps
- Create a new project using the following command
  ```shell
    mvn io.quarkus.platform:quarkus-maven-plugin:2.15.1.Final:create \
    -DprojectGroupId=quarkus.mservices.offerdiscounts \
    -DprojectArtifactId=discount-coupons-producers-kafka \
    -Dextensions='resteasy-reactive-jackson,smallrye-reactive-messaging-kafka' \
    -DnoCode
  ```
- Add the following dependencies in discount-coupons-producers-kafka/pom.xml
  ```xml
  <dependency>
     <groupId>io.quarkus</groupId>
     <artifactId>quarkus-rest-client-reactive-deployment</artifactId>
  </dependency>
  <dependency>
     <groupId>org.awaitility</groupId>
     <artifactId>awaitility</artifactId>
     <version>4.2.0</version>
  </dependency>
  ```
  
- Create another project using the following command
  ```shell
    quarkus create app quarkus.mservices.offerdiscounts:discount-coupons-processor-kafka \
    --extension='smallrye-reactive-messaging-kafka' \
    -DnoCode
  ```
  
- Note the above projects are added as modules in the parent pom.xml, if not add t
  ```xml
  <modules>
   ...
    <module>discount-coupons-producers-kafka</module>
    <module>discount-coupons-processor-kafka</module>
  </modules>
  ```
#### In the module discount-coupons-producers-kafka
- Create the discount request and discount bean
- Create the DiscountCoupon and DiscountCouponDeserializer classes
- Create the DiscountCouponProducer and DiscountCouponProducerInterface classes


- Run the application as below

  
  ```bash
   mvn -f discount-coupons-producers-kafka quarkus:dev -Ddebug=5002 -Dquarkus.http.port=8091
  ```

#### In the module discount-coupons-processor-kafka
- Create the DiscountCouponProcessor class
- Copy the DiscountCoupon and DiscountCouponSerializer classes from the discount-coupons-producers-kafka module
- Update the application.properties file with the below properties
- Update the application.properties file
  ```properties
   %dev.quarkus.http.port=8081

    # Go bad to the first records, if it's out first access
    kafka.auto.offset.reset=earliest
    
    # Set the Kafka topic, as it's not the channel name
    mp.messaging.incoming.disco-requests.topic = disco-requests
    
    # Configure the outgoing `discocopons` Kafka topic
    mp.messaging.outgoing.discocoupons.value.serializer=io.quarkus.kafka.client.serialization.ObjectMapperSerializer
  ```
- Run the application as below

  
  ```bash
   mvn -f discount-coupons-processor-kafka quarkus:dev -Ddebug=5004
  ```

- Test the application using the following command
  ```shell
     curl -X POST  http://localhost:8093/discounts/request
 ```

#### Deploy the app in kubernetes
- Create the docker images for the modules
  ```shell
  mvn -f discount-coupons-producers-kafka clean package -Dquarkus.container-image.push=true
  mvn -f discount-coupons-processor-kafka clean package -Dquarkus.container-image.push=true
  ```
- Create the kubernetes resources
  ```shell
  mvn -f discount-coupons-producers-kafka clean package -Dquarkus.kubernetes.deploy=true
  mvn -f discount-coupons-processor-kafka clean package -Dquarkus.kubernetes.deploy=true
  ```
- Create the docker compose file and convert it to kubernetes resources
- Deploy it in kubernetes
  ```shell
  kubectl apply -f 01-zookeeper.yaml
  kubectl apply -f 02-kafka.yaml
  kubectl apply -f 03-applications.yaml
  ```
- Access the application
  ```url
  http://localhost:8081/discount-coupons
  ```

#### Ex-19: API Gateway with Istio

#### Ex-20: Continuous Delivery with ArgoCD

#### Ex-21: Deploy the native image (jib)

#### Ex-22: Deploy the s2i image (openshift)

#### Ex-23: Helm and Pulumi