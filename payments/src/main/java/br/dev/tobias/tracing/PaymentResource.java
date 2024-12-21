package br.dev.tobias.tracing;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/rest/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @POST
    public RestResponse<?> receivePayment(PaymentDTO paymentDTO) {
        try {
            paymentService.receivePayment(paymentDTO);
            return RestResponse.ok();
        } catch (PaymentInvalidException e) {
            return RestResponse.status(RestResponse.Status.BAD_REQUEST);
        }
    }
}
