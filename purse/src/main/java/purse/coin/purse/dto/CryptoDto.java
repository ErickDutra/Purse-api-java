package purse.coin.purse.dto;

import java.math.BigDecimal;

public record CryptoDto(
    String id,
    String symbol,
    String name,
    BigDecimal valueUSD
) {
}
