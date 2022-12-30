package quarkus.mservices.offerdiscounts.producer;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import quarkus.mservices.offerdiscounts.model.DiscountCoupon;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

/**
 * A bean consuming data from the "disco-requests" Kafka topic (mapped to "requests" channel) and giving out a random discount.
 * The result is pushed to the "discocoupons" Kafka topic.
 */

@ApplicationScoped
public class DiscountCouponProcessor {

    private final Random random = new Random();

    @Incoming("disco-requests")
    @Outgoing("discocoupons")
    @Blocking
    public DiscountCoupon process(String discoRequest) {
        return new DiscountCoupon(discoRequest, random.nextInt(30));
    }

}
