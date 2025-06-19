package purse.coin.purse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import purse.coin.purse.model.Crypto;



@Repository
public interface CryptoRepository extends JpaRepository<Crypto, String> {

}
