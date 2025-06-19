package purse.coin.purse.dto;

import java.math.BigDecimal;
import java.util.Date;

import purse.coin.purse.model.enums.TransactionStatus;
import purse.coin.purse.model.enums.TransactionType;

public record TransactionsDto(
     String id,
     String idAddressOrigin,
     String idAddressDestination,
     String idTransaction,
     String idCrypto,
     BigDecimal amount,
     BigDecimal tax,
     Date date, 
     TransactionStatus status,
     TransactionType type) {
}
