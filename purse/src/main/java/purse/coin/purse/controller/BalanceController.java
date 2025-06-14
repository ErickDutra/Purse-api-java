package purse.coin.purse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import purse.coin.purse.dto.BalanceDto;
import purse.coin.purse.service.BalanceService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/balance")
public class BalanceController {
    

    BalanceService balanceService;
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/getBalance")
    public ResponseEntity<BalanceDto> getBalance(@RequestBody String address) {
        BalanceDto balance = balanceService.getBalance(address);
        if (balance == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(balance);
    }

    
}
