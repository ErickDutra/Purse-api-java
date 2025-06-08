package purse.coin.purse.service;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import purse.coin.purse.dto.SendTransactionDto;
import purse.coin.purse.integration.TransactionIntegrationService;
import purse.coin.purse.integration.dto.TransactionIntegrationDto;
import purse.coin.purse.model.TransactionUsers;
import purse.coin.purse.model.enums.TransactionStatus;
import purse.coin.purse.model.enums.TransactionType;
import purse.coin.purse.model.enums.ValueTax;
import purse.coin.purse.repository.TransactionUsersRepository;

@Service
public class TransactionUsersService {

    private final TransactionUsersRepository transactionUsersRepository;
    private final TransactionIntegrationService transectionIntegrationService;

    public TransactionUsersService(TransactionUsersRepository transactionUsersRepository,
            TransactionIntegrationService transectionIntegrationService) {
        this.transactionUsersRepository = transactionUsersRepository;
        this.transectionIntegrationService = transectionIntegrationService;
    }

    @Transactional
    public ResponseEntity<SendTransactionDto> sendTransaction(SendTransactionDto transactionDto) {

        TransactionUsers transactionUser = new TransactionUsers();
        transactionUser.setIdWalletDestination(transactionDto.idWalletDestination());
        transactionUser.setIdWalletOrigin(transactionDto.idWalletOrigin());
        transactionUser.setAmount(transactionDto.amount());
        if (transactionDto.amount() != null) {
            transactionUser
                    .setTax(transactionDto.amount().multiply(new java.math.BigDecimal(ValueTax.VALUE_TAX.getValue())));
        }
        transactionUser.setIdCripto("1");
        transactionUser.setStatus(TransactionStatus.PENDING);
        transactionUser.setType(TransactionType.TRANSFER);
        transactionUser.setDate(new Date());
        // String sender, String receiver, double amount, double tax

        TransactionIntegrationDto transactionIntegrationDto = new TransactionIntegrationDto(null,
                transactionDto.idWalletOrigin(), transactionDto.idWalletDestination(), transactionDto.amount(),
                transactionUser.getTax());
        ResponseEntity<TransactionIntegrationDto> response = transectionIntegrationService
                .postTransaction(transactionIntegrationDto);
        TransactionIntegrationDto responseBody = response.getBody();
        if (responseBody != null) {
            transactionUser.setIdTransaction(responseBody.id_transaction());
        }
        transactionUsersRepository.save(transactionUser);
        return ResponseEntity.ok(transactionDto);
    }

    public TransactionUsers getAllTransactionUsers() {
        return transactionUsersRepository.findAll().stream().findFirst().orElse(null);
    }

}
