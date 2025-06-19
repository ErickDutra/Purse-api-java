package purse.coin.purse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import purse.coin.purse.dto.AcceptTransactionPendingDto;
import purse.coin.purse.dto.SendTransactionDto;
import purse.coin.purse.dto.TransactionsDto;
import purse.coin.purse.service.TransactionUsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@CrossOrigin(origins = "*")
public class TransactionUsersController {

    TransactionUsersService transactionUsersService;
    public TransactionUsersController(TransactionUsersService transactionUsersService) {
        this.transactionUsersService = transactionUsersService;
    }


    @PostMapping("/send")
     public ResponseEntity<SendTransactionDto> sendTransaction(@RequestBody SendTransactionDto sendTransactionDto) {
        return transactionUsersService.sendTransaction(sendTransactionDto);
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<TransactionsDto>> getTransactions() {
        return ResponseEntity.ok(transactionUsersService.getAllTransactionUsers());
    }

    @PostMapping("/acceptTransaction")
    public ResponseEntity<AcceptTransactionPendingDto> acceptTransaction(@RequestBody AcceptTransactionPendingDto acceptTransactionPendingDto) {
        return transactionUsersService.acceptTransactionPending(acceptTransactionPendingDto);
    }

    @GetMapping("/getTransactionsDestination/{id}")
    public ResponseEntity<List<TransactionsDto>> getTransactionsDestination( @PathVariable String id) {
        return ResponseEntity.ok(transactionUsersService.getAllTransactionIntegrationDestination(id));
    }

    @GetMapping("/getTransactionsOrigin/{id}")
    public ResponseEntity<List<TransactionsDto>> getTransactionsOrigin( @PathVariable String id) {
        return ResponseEntity.ok(transactionUsersService.getAllTransactionIntegrationOrigin(id));
    }
 
}
