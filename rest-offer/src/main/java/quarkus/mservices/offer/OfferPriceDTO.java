package quarkus.mservices.offer;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OfferPriceDTO {
    private String id;
    private String offerId;
    private BigDecimal price;
    private String currency;
}
