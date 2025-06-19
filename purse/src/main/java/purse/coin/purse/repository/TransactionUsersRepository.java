package purse.coin.purse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import purse.coin.purse.model.TransactionUsers;

@Repository
public interface TransactionUsersRepository extends JpaRepository<TransactionUsers, String> {
    TransactionUsers findByIdTransaction(String idTransaction);
    List<TransactionUsers> findAllByidAddressDestination(String idUser);
    List<TransactionUsers> findAllByidAddressOrigin(String idUser);
}
