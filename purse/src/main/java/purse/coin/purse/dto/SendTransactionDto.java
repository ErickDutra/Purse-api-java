package purse.coin.purse.dto;

import java.math.BigDecimal;

public record SendTransactionDto(String idAddressOrigin,
        String idAddressDestination,
        String idCrypto,
        BigDecimal amount,
        String date) {

}
