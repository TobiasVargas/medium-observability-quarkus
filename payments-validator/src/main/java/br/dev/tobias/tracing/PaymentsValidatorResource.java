package br.dev.tobias.tracing;

import io.quarkus.logging.Log;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Random;

@Path("/rest/v1/payments-validator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentsValidatorResource {

    @POST
    @Path("/")
    public RestResponse<PaymentValidatedDTO> validate(PaymentDTO paymentDTO) {
        boolean approved = new Random().nextBoolean();

        if (approved) {
            Log.info("Payment with amount " + paymentDTO.amount() + " approved");
            var response = new PaymentValidatedDTO("Approved", paymentDTO.buyerDocument(), paymentDTO.amount());
            return RestResponse.ok(response);
        }

        Log.info("Payment with amount " + paymentDTO.amount() + " not approved");
        var response = new PaymentValidatedDTO("Not approved", paymentDTO.buyerDocument(), paymentDTO.amount());
        return RestResponse.status(RestResponse.Status.BAD_REQUEST, response);
    }
}
