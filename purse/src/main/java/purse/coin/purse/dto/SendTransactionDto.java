package purse.coin.purse.dto;

import java.math.BigDecimal;

public record SendTransactionDto(String idWalletOrigin,
        String idWalletDestination,
        String idCripto,
        BigDecimal amount,
        String date) {

}
