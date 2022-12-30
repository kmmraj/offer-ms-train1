package quarkus.mservices.offerdiscounts.producer;

import io.smallrye.mutiny.Multi;
import quarkus.mservices.offerdiscounts.model.DiscountCoupon;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/discounts")
public interface DiscountCouponProducerInterface {
    @GET
    @Path("/discocoupons")
    @Produces(MediaType.SERVER_SENT_EVENTS)
        // denotes that server side events (SSE) will be produced
    Multi<DiscountCoupon> streamDiscos();
}
