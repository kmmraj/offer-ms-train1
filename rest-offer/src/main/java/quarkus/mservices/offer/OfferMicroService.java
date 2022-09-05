package quarkus.mservices.offer;


import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Offer API",
                description = "Returns Offers",
                version = "1.0",
                contact = @Contact(name = "@kmmraj", url = "https://twitter.com/kmmraj")),
        externalDocs = @ExternalDocumentation(url = "https://rndwww.", description = "All APIs of the offers microservice"),
        tags = {
                @Tag(name = "Offer", description = "Offer related operations")
        }
)

public class OfferMicroService extends Application {
}
