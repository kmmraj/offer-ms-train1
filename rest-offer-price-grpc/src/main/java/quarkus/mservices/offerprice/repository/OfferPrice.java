package quarkus.mservices.offerprice.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class OfferPrice extends PanacheEntityBase {
    @Id
    private String id;
    private String offerId;
    private BigDecimal price;
    private String currency;
    private BigDecimal tax;
}
