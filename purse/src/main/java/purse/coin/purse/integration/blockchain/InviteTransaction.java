package purse.coin.purse.integration.blockchain;

import org.springframework.cloud.openfeign.FeignClient;

import feign.Headers;
import feign.RequestLine;
import purse.coin.purse.integration.dto.TransactionIntegrationDto;

@FeignClient(name = "transaction", url = "http://localhost:8000")
public interface InviteTransaction {

    @RequestLine("POST /transaction")
    @Headers("Content-Type: application/json")
    TransactionIntegrationDto postTransaction(TransactionIntegrationDto transactionDto);
}
