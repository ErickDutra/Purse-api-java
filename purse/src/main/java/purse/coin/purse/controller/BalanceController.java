package purse.coin.purse.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import purse.coin.purse.dto.BalanceDto;
import purse.coin.purse.model.Balance;
import purse.coin.purse.service.BalanceService;
import org.springframework.web.bind.annotation.PostMapping;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/balance")
public class BalanceController {
    

    BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/getBalance/{address}")
    public ResponseEntity<BalanceDto> getBalanceAll(@PathVariable String address) {
        BalanceDto balance = balanceService.getBalance(address);
        if (balance == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/postBalance/{idUser}")
     public Balance postBalance(@PathVariable String idUser){
        Balance balance = balanceService.postBalance(idUser);
        if (balance == null) {
            return null;
        }
        return balance;

     }

    @GetMapping("/getBalanceByIdWalletAndIdCrypto/{idWallet}/{idCrypto}")
    public ResponseEntity<Balance> getBalanceByIdWalletAndIdCrypto(@PathVariable String idWallet, @PathVariable String idCrypto) {
        Balance balance = balanceService.getBalanceByIdWalletAndIdCrypto(idWallet, idCrypto);
        if (balance == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/getBalanceByIdWallet/{idWallet}")
    public ResponseEntity<List<Balance>> getBalanceByIdWallet(@PathVariable String idWallet) {
        List<Balance> balance = balanceService.getAllBalancesByIdWallet(idWallet);
        if (balance == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(balance);
    }

    @DeleteMapping("/deleteBalance/{id}")
    public void deleteBalance(@PathVariable String id) {
          balanceService.deleBalance(id);
    }
  
}
