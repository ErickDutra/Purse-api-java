package purse.coin.purse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import purse.coin.purse.model.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, String> {
    
    List<Balance> findAllByIdWallet(String idWallet);

    Balance findByIdWalletAndIdCrypto(String idWallet, String idCrypto);
}
