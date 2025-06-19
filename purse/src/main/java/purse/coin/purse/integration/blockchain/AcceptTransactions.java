package purse.coin.purse.integration.blockchain;

import org.springframework.cloud.openfeign.FeignClient;

import feign.Headers;
import feign.RequestLine;
import purse.coin.purse.integration.dto.AcceptTransactionDto;

@FeignClient(name = "transaction", url = "http://localhost:8000")
public interface AcceptTransactions{

    @RequestLine("POST /transaction/accept/")
    @Headers("Content-Type: application/json")
    AcceptTransactionDto acceptTransaction(AcceptTransactionDto acceptTransactionDto);
    
}
