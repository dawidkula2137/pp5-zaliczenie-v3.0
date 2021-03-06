package pl.projekt.voucherstore.sales.payment;

import pl.projekt.payu.exceptions.PayUException;

public class PaymentException extends IllegalStateException {
    public PaymentException(PayUException e) {
        super(e);
    }
}
