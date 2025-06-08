package purse.coin.purse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import purse.coin.purse.dto.SendTransactionDto;
import purse.coin.purse.model.TransactionUsers;
import purse.coin.purse.service.TransactionUsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
    public ResponseEntity<TransactionUsers> getTransactions() {
        return ResponseEntity.ok(transactionUsersService.getAllTransactionUsers());
    }
   

}
