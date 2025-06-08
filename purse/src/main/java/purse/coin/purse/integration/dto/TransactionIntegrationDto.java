package purse.coin.purse.integration.dto;

import java.math.BigDecimal;

public record TransactionIntegrationDto(String id_transaction, String sender, String receiver, BigDecimal amount, BigDecimal tax) {
}
// sender string, receiver string, amount float64, tax float64