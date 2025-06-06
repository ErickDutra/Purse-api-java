package purse.coin.purse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import purse.coin.purse.model.TransactionUsers;

@Repository
public interface TransactionUsersRepository extends JpaRepository<TransactionUsers, String> {
}
