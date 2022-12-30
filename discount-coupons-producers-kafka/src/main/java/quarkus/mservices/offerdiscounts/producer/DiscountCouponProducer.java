package quarkus.mservices.offerdiscounts.producer;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;
import quarkus.mservices.offerdiscounts.model.DiscountCoupon;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Path("/discounts")
public class DiscountCouponProducer implements DiscountCouponProducerInterface {

    @Inject
    Logger logger;

    @ConfigProperty(name = "quarkus.http.host")
    String host;

    @ConfigProperty(name = "quarkus.http.port")
    String port;

    @Channel("disco-requests")
    Emitter<String> quoteRequestEmitter;

    @Channel("discocoupons")
    Multi<DiscountCoupon> discountCouponMulti;


    @Override
    @Path("/discocoupons")
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<DiscountCoupon> streamDiscos() {
        return discountCouponMulti.log();
    }


    /**
     * Endpoint to generate a new discount request id and send it to "discount-requests" Kafka topic using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.APPLICATION_JSON)
    public DiscountCoupon createRequest() {
        final UUID uuid = UUID.randomUUID();
        quoteRequestEmitter.send(uuid.toString());
        var discountCoupon = new DiscountCoupon(uuid.toString(),0);
        URI uriBase = UriBuilder.fromUri("http://" + host + ":" + port + "/").build();
        RestClientBuilder.newBuilder().baseUri(uriBase).build(DiscountCouponProducerInterface.class).streamDiscos()
                .subscribe().with(
                coupon -> {
                    logger.info(coupon);
                    discountCoupon.discountAmount = coupon.discountAmount;
                },
                failure -> logger.error("Failed with " , failure)
        );

        await().atMost(5, TimeUnit.SECONDS).until(() -> discountCoupon.discountAmount != 0);
        return discountCoupon;
    }

}
