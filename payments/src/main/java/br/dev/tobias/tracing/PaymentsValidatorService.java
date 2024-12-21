package br.dev.tobias.tracing;

import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:8001/rest/v1/payments-validator")
public interface PaymentsValidatorService {

    @POST
    @Path("/")
    PaymentValidatedDTO validate(PaymentDTO paymentDTO);
}
