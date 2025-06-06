package purse.coin.purse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import purse.coin.purse.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    
}
