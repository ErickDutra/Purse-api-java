package purse.coin.purse.model;


import java.math.BigDecimal;

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
public class TransactionUsers {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;

    private String idWalletOrigin;
    private String idWalletDstination;
    private String idCripto;
    private BigDecimal amount;
    private BigDecimal valueUSD;
    private BigDecimal tax;
    private String date;   
}
