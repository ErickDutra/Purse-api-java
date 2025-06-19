package purse.coin.purse.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import purse.coin.purse.dto.AcceptTransactionPendingDto;
import purse.coin.purse.dto.SendTransactionDto;
import purse.coin.purse.dto.TransactionsDto;
import purse.coin.purse.integration.TransactionIntegrationService;
import purse.coin.purse.integration.dto.AcceptTransactionDto;
import purse.coin.purse.integration.dto.TransactionIntegrationDto;
import purse.coin.purse.model.Balance;
import purse.coin.purse.model.TransactionUsers;
import purse.coin.purse.model.Wallet;
import purse.coin.purse.model.enums.TransactionStatus;
import purse.coin.purse.model.enums.TransactionType;
import purse.coin.purse.model.enums.ValueTax;
import purse.coin.purse.repository.BalanceRepository;
import purse.coin.purse.repository.TransactionUsersRepository;
import purse.coin.purse.repository.WalletRepository;

@Service
public class TransactionUsersService {

    private final TransactionUsersRepository transactionUsersRepository;
    private final TransactionIntegrationService transectionIntegrationService;
    private final BalanceRepository balanceRepository;
    private final WalletRepository walletRepository;

    public TransactionUsersService(TransactionUsersRepository transactionUsersRepository,
            TransactionIntegrationService transectionIntegrationService,
            BalanceRepository balanceRepository,
            WalletRepository walletRepository) {
        this.transactionUsersRepository = transactionUsersRepository;
        this.transectionIntegrationService = transectionIntegrationService;
        this.balanceRepository = balanceRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public ResponseEntity<SendTransactionDto> sendTransaction(SendTransactionDto transactionDto) {

        TransactionUsers transactionUser = new TransactionUsers();
        transactionUser.setIdAddressDestination(transactionDto.idAddressDestination());
        transactionUser.setIdAddressOrigin(transactionDto.idAddressOrigin());
        transactionUser.setAmount(transactionDto.amount());
        if (transactionDto.amount() != null) {
            transactionUser
                    .setTax(transactionDto.amount().multiply(new java.math.BigDecimal(ValueTax.VALUE_TAX.getValue())));
        }
        transactionUser.setIdCrypto(transactionDto.idCrypto());
        transactionUser.setStatus(TransactionStatus.PENDING);
        transactionUser.setType(TransactionType.TRANSFER);
        transactionUser.setDate(new Date());
        // String sender, String receiver, double amount, double tax

        TransactionIntegrationDto transactionIntegrationDto = new TransactionIntegrationDto(null,
                transactionDto.idAddressOrigin(), transactionDto.idAddressDestination(), transactionDto.amount(),
                transactionUser.getTax());
        ResponseEntity<TransactionIntegrationDto> response = transectionIntegrationService
                .postTransaction(transactionIntegrationDto);
        TransactionIntegrationDto responseBody = response.getBody();
        if (responseBody != null) {
            transactionUser.setIdTransaction(responseBody.id_transaction());
        }
        transactionUsersRepository.save(transactionUser);

        return ResponseEntity.ok(transactionDto);
    }

    public List<TransactionsDto> getAllTransactionIntegrationDestination(String id) {
        List<TransactionUsers> transactionUsersList = transactionUsersRepository
                .findAllByidAddressDestination(id);
        List<TransactionsDto> transactionsDtoList = new ArrayList<>();

        for (TransactionUsers transaction : transactionUsersList) {
            TransactionsDto transactionsDto = new TransactionsDto(
                transaction.getId(),
                transaction.getIdAddressOrigin(),
                transaction.getIdAddressDestination(),
                transaction.getIdTransaction(),
                transaction.getIdCrypto(),
                transaction.getAmount(),
                transaction.getTax(),
                transaction.getDate(),
                transaction.getStatus(),
                transaction.getType()
            );
            transactionsDtoList.add(transactionsDto);
        }
        return transactionsDtoList;
        
    }

    
    public List<TransactionsDto> getAllTransactionIntegrationOrigin(String id) {
        List<TransactionUsers> transactionUsersList = transactionUsersRepository.findAllByidAddressOrigin(id);
        List<TransactionsDto> transactionsDtoList = new ArrayList<>();

        for (TransactionUsers transaction : transactionUsersList) {
            TransactionsDto transactionsDto = new TransactionsDto(
                transaction.getId(),
                transaction.getIdAddressOrigin(),
                transaction.getIdAddressDestination(),
                transaction.getIdTransaction(),
                transaction.getIdCrypto(),
                transaction.getAmount(),
                transaction.getTax(),
                transaction.getDate(),
                transaction.getStatus(),
                transaction.getType()
            );
            transactionsDtoList.add(transactionsDto);
        }
        return transactionsDtoList;
    }

    public List<TransactionsDto> getAllTransactionUsers() {
    List<TransactionUsers> transactionUsersList = transactionUsersRepository.findAll();
    List<TransactionsDto> transactionsDtoList = new ArrayList<>();

    for (TransactionUsers transaction : transactionUsersList) {
        TransactionsDto transactionsDto = new TransactionsDto(
            transaction.getId(),
            transaction.getIdAddressOrigin(),
            transaction.getIdAddressDestination(),
            transaction.getIdTransaction(),
            transaction.getIdCrypto(),
            transaction.getAmount(),
            transaction.getTax(),
            transaction.getDate(),
            transaction.getStatus(),
            transaction.getType()
        );
        transactionsDtoList.add(transactionsDto);
    }

    return transactionsDtoList;
}

    @Transactional
    public ResponseEntity<AcceptTransactionPendingDto> acceptTransactionPending(
        AcceptTransactionPendingDto acceptTransactionPendingDto) {
        TransactionUsers transactionDto = transactionUsersRepository
                .findByIdTransaction(acceptTransactionPendingDto.transactionID());
            
        Wallet walletOrigin = walletRepository
                .findByAddress(transactionDto.getIdAddressOrigin());
        if (walletOrigin == null) {
            System.out.println("Wallet origin not found: " + transactionDto.getIdAddressOrigin());
            return ResponseEntity.badRequest().body(null);
        }
        Wallet walletDestination = walletRepository
                .findByAddress(transactionDto.getIdAddressDestination());
        if (walletDestination == null) {
            System.out.println("Wallet destini not found: " + transactionDto.getIdAddressDestination());
            return ResponseEntity.badRequest().body(null);
        }
        if (transactionDto.getAmount() == null || transactionDto.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid amount: " + transactionDto.getAmount());
            return ResponseEntity.badRequest().body(null);
        }

        if (transactionDto.getIdAddressOrigin().equals(transactionDto.getIdAddressDestination())) {
            System.out.println("Origin and destination addresses are the same.");
            return ResponseEntity.badRequest().body(null);
        }
        Balance balanceOrigin = balanceRepository
                .findByIdWalletAndIdCrypto(walletOrigin.getId(), 
                        transactionDto.getIdCrypto());
        if (balanceOrigin == null) {
            System.out.println("Balance origin not found for wallet: " + walletOrigin.getId());
            return ResponseEntity.badRequest().body(null);
        }
        if (balanceOrigin.getAmount() == null
                || balanceOrigin.getAmount().compareTo(transactionDto.getAmount()) < 0) {
            System.out.println("Insufficient balance in origin wallet: " + walletOrigin.getId());                             
            return ResponseEntity.badRequest().body(null);
        }

        Balance balanceDestination = balanceRepository
                .findByIdWalletAndIdCrypto(walletDestination.getId(), 
                        transactionDto.getIdCrypto());
        if (balanceDestination == null) {
            System.out.println("Balance destination not found for wallet: " + walletDestination.getId());
            return ResponseEntity.badRequest().body(null);
        }
        balanceOrigin.setAmount(
                balanceOrigin.getAmount().subtract(transactionDto.getAmount()));
        balanceDestination.setAmount(
                balanceDestination.getAmount().add(transactionDto.getAmount()));
        balanceRepository.save(balanceOrigin);
        balanceRepository.save(balanceDestination);

        TransactionUsers transaction = transactionUsersRepository.findByIdTransaction(acceptTransactionPendingDto.transactionID());
        if (transaction == null) {
            System.out.println("Transaction not found: " + acceptTransactionPendingDto.transactionID());
            return null;
        }

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            System.out.println("Transaction is not pending: " + transaction.getStatus());
            return null;
        }
        AcceptTransactionDto acceptTransactionDto = new AcceptTransactionDto(
                acceptTransactionPendingDto.transactionID(),
                acceptTransactionPendingDto.signature()
                );

        transaction.setStatus(TransactionStatus.CONFIRMED);
        transactionUsersRepository.save(transaction);

        transectionIntegrationService.acceptTransaction(acceptTransactionDto);
        return ResponseEntity.ok(acceptTransactionPendingDto);
    }

}
