package br.dev.tobias.tracing;

import java.math.BigDecimal;

public record PaymentDTO(String buyerDocument, BigDecimal amount) {
}
