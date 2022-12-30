package quarkus.mservices.offerdiscounts.model;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class DiscountCouponDeserializer extends ObjectMapperDeserializer<DiscountCoupon> {
    public DiscountCouponDeserializer() {
        super(DiscountCoupon.class);
    }
}
