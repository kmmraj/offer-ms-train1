package quarkus.mservices.offerprice;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;
import quarkus.mservices.offerprice.repository.OfferPrice;
import quarkus.mservices.offerprice.repository.OfferPriceRepository;
import quarkus.mservices.offerprice.OfferPriceRequest;
import quarkus.mservices.offerprice.OfferPriceResponse;
import quarkus.mservices.offerprice.OfferPriceServiceInterface;

import javax.inject.Inject;

@GrpcService
public class OfferPriceService implements OfferPriceServiceInterface {

    @Inject
    OfferPriceRepository offerPriceRepository;

    @Inject
    Logger logger;

//    @Override
//    public void getOfferPrice(OfferPriceRequest request, StreamObserver<OfferPriceResponse> responseObserver) {
//        OfferPriceResponse response = OfferPriceResponse.newBuilder()
//                .setPrice(100)
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }

    @Override
    @Blocking
    public Uni<OfferPriceResponse> getOfferPrice(OfferPriceRequest request) {
        logger.info("getOfferPrice Service call with: " + request.getOfferId());
        OfferPrice offerPrice =  offerPriceRepository.getOffersPriceByOfferId(request.getOfferId());
        return Uni.createFrom().item(OfferPriceResponse.newBuilder()
                .setPrice(offerPrice.getPrice().toString())
                .setOfferId(request.getOfferId())
                .setCurrency(offerPrice.getCurrency())
                .setId(offerPrice.getId())
                .build());

//        return Uni.createFrom().item(OfferPriceResponse.newBuilder()
//                .setPrice("100")
//                .setOfferId(request.getOfferId())
//                .setCurrency("EUR")
//                .setId("1")
//                .build());
    }
}
