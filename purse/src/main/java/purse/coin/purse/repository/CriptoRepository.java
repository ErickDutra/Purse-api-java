package purse.coin.purse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import purse.coin.purse.model.Cripto;

@Repository
public interface CriptoRepository extends JpaRepository<Cripto, String> {
    
}
