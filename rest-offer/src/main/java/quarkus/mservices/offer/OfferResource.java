package quarkus.mservices.offer;

import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.auth.AuthPermission;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Tag(name = "Resource for Offer APIs")
@Schema(description = "OfferResource API")
@Path("/api")
public class OfferResource {

    @Inject
    Logger logger;

    @Inject
    SecurityIdentity securityIdentity;
// TODO : 2 -- Check this

    @Inject
    JsonWebToken jwt;

//    @Inject
//    @Claim("scope")
//    Set<String> scopes;

    @ConfigProperty(name = "offer.default.travel.days", defaultValue = "30")
    int defaultTravelDays;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/offers")
    @NoCache

   // @Authenticated
//    @RolesAllowed("user")
  //  @RolesAllowed({"user","admin"})
    //@PermitAll
    public List<Offer> getOffers() {
        jwt.getClaimNames();
        jwt.getClaim("scope");
       // scopes.size();
        securityIdentity.getAttributes();
        securityIdentity.checkPermissionBlocking(new AuthPermission("offer:read"));
        Offer offerOne = new Offer();
        offerOne.setId(UUID.randomUUID().toString().substring(0, 8));
        offerOne.setCabinClass(CabinClassEnum.ECONOMY);
        offerOne.setDestination("MAD");
        offerOne.setFlightId(UUID.randomUUID().toString().substring(0, 5));
        offerOne.setOrigin("BCN");
        offerOne.setDepartureDate(Date.from(Instant.now().plus(defaultTravelDays, ChronoUnit.DAYS)));
        logger.info(" Offer One is:: " + offerOne);

        return List.of(offerOne);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @NoCache
    @Path("/user")
    public SecurityIdentity getUserInfo() {
        logger.info(" getUserInfo called: ");
        return securityIdentity;
    }
}