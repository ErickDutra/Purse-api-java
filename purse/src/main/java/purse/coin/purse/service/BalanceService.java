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
    

    public BalanceDto getBalance(String address) {
    Wallet wallet = walletRepository.findByAddress(address);
    if (wallet == null) {
        return null;
    }
    try {
        List<Balance> balanceList = balanceRepository.findAllByIdWallet(wallet.getId());
        System.out.println("Balance List: " + balanceList);
        if (balanceList == null || balanceList.isEmpty()) {
            return new BalanceDto(BigDecimal.ZERO);
        }
        BigDecimal valueUSD = BigDecimal.ZERO;
        for (Balance bal : balanceList) {
            if (bal.getIdCrypto() == null || bal.getAmount() == null) {
                continue; 
            }
            Crypto crypto = cryptoRepository.findById(bal.getIdCrypto()).orElse(null);
            if (crypto == null) {
                continue;
            }
            valueUSD = valueUSD.add(crypto.getValueUSD().multiply(bal.getAmount()));
        }
    return new BalanceDto(valueUSD);
    } catch (Exception e) {
        System.out.println( e.getMessage());
    }
        return null;
    }

    public Balance getBalanceByIdWalletAndIdCrypto(String idWallet, String idCrypto) {
        return balanceRepository.findByIdWalletAndIdCrypto(idWallet, idCrypto);
    }

    public List<Balance> getAllBalancesByIdWallet(String idWallet) {
        return balanceRepository.findAllByIdWallet(idWallet);
    }

    public Balance deleBalance(String id) {
        Balance balance =  balanceRepository.findById(id).orElse(null);
        if (balance == null) {
            return null;
        }
        balanceRepository.delete(balance);
        return balance;
    }


    public Balance postBalance(String idUser) {
        Wallet wallet = walletRepository.findByIdUser(idUser);
        if (wallet == null) {
            return null;
        }
        Balance balance = balanceRepository.findByIdWalletAndIdCrypto(wallet.getId(), "5dceac38-be39-4061-8d4c-4fab46628dc4");
        if (balance == null) {
            balance = new Balance();
            balance.setIdWallet(wallet.getId());
            balance.setIdCrypto("5dceac38-be39-4061-8d4c-4fab46628dc4");
            balance.setAmount(BigDecimal.ZERO);
        }
        balance.setIdWallet(wallet.getId());
        balance.setAmount(balance.getAmount().add(BigDecimal.valueOf(100)));
        return balanceRepository.save(balance);
    }
}
