package purse.coin.purse.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;
    private String idUser;
    private Date createdAt;
}
