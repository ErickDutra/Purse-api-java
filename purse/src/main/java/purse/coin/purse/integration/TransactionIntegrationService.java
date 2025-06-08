package purse.coin.purse.integration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import purse.coin.purse.integration.blockchain.InviteTransaction;
import purse.coin.purse.integration.dto.TransactionIntegrationDto;

@Service
@RequiredArgsConstructor
public class TransactionIntegrationService {

    private final InviteTransaction inviteTransaction;
    
    public ResponseEntity<TransactionIntegrationDto> postTransaction(TransactionIntegrationDto transactionDto) {
        return ResponseEntity.ok(inviteTransaction.postTransaction(transactionDto));
    }
}
