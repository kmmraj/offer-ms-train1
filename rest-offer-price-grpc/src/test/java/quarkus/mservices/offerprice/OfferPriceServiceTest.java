package quarkus.mservices.offerprice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

@QuarkusTest
public class OfferPriceServiceTest {

    @GrpcClient
    OfferPriceServiceInterface offerPriceGrpc;

    @Test
    public void testHello() {
        OfferPriceResponse reply = offerPriceGrpc
                .getOfferPrice(OfferPriceRequest.newBuilder().setOfferId("1").build())
                .await().atMost(Duration.ofSeconds(5));
        //assertEquals("Hello Neo!", reply);
        assertNotNull(reply);
    }

}
