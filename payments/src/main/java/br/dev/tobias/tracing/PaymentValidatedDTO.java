package br.dev.tobias.tracing;

import java.math.BigDecimal;

public record PaymentValidatedDTO(String message, String buyerDocument, BigDecimal amount) {
}
