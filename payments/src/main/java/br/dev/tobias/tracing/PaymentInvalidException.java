package br.dev.tobias.tracing;

public class PaymentInvalidException extends Exception {
    public PaymentInvalidException(String message) {
        super(message);
    }
}
