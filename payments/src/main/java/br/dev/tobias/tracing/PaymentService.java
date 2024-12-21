package br.dev.tobias.tracing;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@ApplicationScoped
public class PaymentService {
    @Inject
    @RestClient
    PaymentsValidatorService paymentsValidatorService;

    @Inject
    Tracer tracer;

    @Inject
    MeterRegistry meterRegistry;

    private static final Logger LOG = Logger.getLogger(PaymentService.class);

    @Transactional
    public void receivePayment(PaymentDTO paymentDTO) throws PaymentInvalidException {
        LOG.info("Payment received: " + paymentDTO);
        try {
            var validationResult = paymentsValidatorService.validate(paymentDTO);
            LOG.info("Payment approved: " + validationResult);

            var span = tracer.spanBuilder("My custom span")
                    .setAttribute("payment-value", validationResult.amount().toString())
                    .setParent(Context.current().with(Span.current()))
                    .setSpanKind(SpanKind.INTERNAL)
                    .startSpan();
            span.end();

            var payment = new Payment(validationResult.buyerDocument(), validationResult.amount());
            payment.persist();
        } catch (ClientWebApplicationException e) {
            LOG.error("Payment failed: " + paymentDTO + " with code " + e.getResponse().getStatus(), e);
            meterRegistry.counter("payments_failed").increment();
            throw new PaymentInvalidException("Payment failed: " + paymentDTO);
        }
    }
}
