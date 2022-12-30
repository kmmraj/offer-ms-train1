package quarkus.mservices.offerdiscounts.model;

public class DiscountCoupon {
    public String id;
    public int discountAmount;

    /**
     * Default constructor required for Jackson serializer
     */
    public DiscountCoupon() {
    }

    public DiscountCoupon(String id, int discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "DiscountCoupon{" +
                "id='" + id + '\'' +
                ", discountAmount=" + discountAmount +
                '}';
    }

}
