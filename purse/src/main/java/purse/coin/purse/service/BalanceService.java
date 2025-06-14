package purse.coin.purse.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import purse.coin.purse.dto.BalanceDto;
import purse.coin.purse.model.Balance;
import purse.coin.purse.model.Crypto;
import purse.coin.purse.model.Wallet;
import purse.coin.purse.repository.BalanceRepository;
import purse.coin.purse.repository.CryptoRepository;
import purse.coin.purse.repository.WalletRepository;

@Service
public class BalanceService {

    BalanceRepository balanceRepository;
    WalletRepository walletRepository;
    CryptoRepository cryptoRepository;

    public BalanceService(BalanceRepository balanceRepository, WalletRepository walletRepository, CryptoRepository cryptoRepository) {
        this.balanceRepository = balanceRepository;
        this.walletRepository = walletRepository;
        this.cryptoRepository = cryptoRepository;
    }
    
     public BalanceDto getBalance(String adress) {
        Wallet wallet = walletRepository.findByIdUser(adress);
        BigDecimal valueUSD = BigDecimal.ZERO;
        if (wallet == null) {
            return null;
        }
        List<Balance> balanceList = balanceRepository.findAllByIdWallet(wallet.getId());
        if (balanceList == null) {
            return null;
        }
        for (Balance bal : balanceList) {
            Crypto crypto = cryptoRepository.findById(bal.getIdCripto()).orElse(null);
            if (crypto == null) {
                return null;
            }
            valueUSD = crypto.getValueUSD().multiply(bal.getAmount());
         
        }

        return new BalanceDto(valueUSD);
       
    }
}
