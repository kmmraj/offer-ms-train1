package quarkus.mservices.offer;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class OfferResourceTest {


    @Test()
    public void testHelloEndpoint() {
        given()
          .when().get("/api/offers")
          .then()
             .statusCode(200);
    }

}