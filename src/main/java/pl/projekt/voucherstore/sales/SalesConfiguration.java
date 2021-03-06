package pl.projekt.voucherstore.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.projekt.payu.http.JavaHttpPayUApiClient;
import pl.projekt.payu.PayU;
import pl.projekt.payu.PayUCredentials;
import pl.projekt.voucherstore.productcatalog.ProductCatalogFacade;
import pl.projekt.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.projekt.voucherstore.sales.offer.OfferMaker;
import pl.projekt.voucherstore.sales.ordering.ReservationRepository;
import pl.projekt.voucherstore.sales.payment.PayUPaymentGateway;
import pl.projekt.voucherstore.sales.payment.PaymentGateway;
import pl.projekt.voucherstore.sales.product.ProductCatalogProductDetailsProvider;
import pl.projekt.voucherstore.sales.product.ProductDetailsProvider;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker, PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
        return new SalesFacade(
                productCatalogFacade,
                new InMemoryBasketStorage(),
                () -> "customer_1",
                (productId) -> true,
                offerMaker,
                paymentGateway,
                reservationRepository);
    }

    @Bean
    PaymentGateway payUPaymentGateway() {
        return new PayUPaymentGateway(new PayU(
                PayUCredentials.productionOfEnv(),
                new JavaHttpPayUApiClient()
        ));
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider) {
        return new OfferMaker(productDetailsProvider);
    }

    @Bean
    ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        return new ProductCatalogProductDetailsProvider(productCatalogFacade);
    }
}
