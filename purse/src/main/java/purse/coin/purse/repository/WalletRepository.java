package purse.coin.purse.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import purse.coin.purse.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
    Wallet findByIdUser(String usersId);
    Wallet findByAddress(String address);
}
