package purse.coin.purse.model;


import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import purse.coin.purse.model.enums.TransactionStatus;
import purse.coin.purse.model.enums.TransactionType;

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
    private String idWalletDestination;
    private String idTransaction;
    private String idCripto;
    private BigDecimal amount;
    private BigDecimal tax;
    private Date date; 
    private TransactionStatus status;
    private TransactionType type;
}
