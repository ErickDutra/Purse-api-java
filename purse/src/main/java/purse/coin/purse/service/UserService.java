package purse.coin.purse.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import purse.coin.purse.dto.BalanceDto;
import purse.coin.purse.dto.LoginDto;
import purse.coin.purse.dto.UserLoginDto;
import purse.coin.purse.dto.UsersDto;
import purse.coin.purse.model.Balance;
import purse.coin.purse.model.Crypto;
import purse.coin.purse.model.Users;
import purse.coin.purse.model.Wallet;
import purse.coin.purse.repository.BalanceRepository;
import purse.coin.purse.repository.CryptoRepository;
import purse.coin.purse.repository.UsersRepository;
import purse.coin.purse.repository.WalletRepository;

@Service
public class UserService {

    UsersRepository usersRepository;
    BalanceRepository balanceRepository;
    WalletRepository walletRepository;
    CryptoRepository cryptoRepository;
    public UserService(UsersRepository usersRepository, 
            BalanceRepository balanceRepository, WalletRepository walletRepository, CryptoRepository cryptoRepository) {
        this.usersRepository = usersRepository;
        this.balanceRepository = balanceRepository; 
        this.walletRepository = walletRepository; 
        this.cryptoRepository =cryptoRepository;
    }

    @Transactional
    public UsersDto cadastrarUser(UsersDto usersDto) {
        if (usersRepository.existsByEmail(usersDto.email())) {
            return null;
        }
        if (usersRepository.existsByCpf(usersDto.cpf())) {
            return null;
        }
        Users user = new Users();
        user.setEmail(usersDto.email()); 
        user.setName(usersDto.name());
        user.setPassword(usersDto.password());
        user.setCpf(usersDto.cpf());
        usersRepository.save(user);
        usersDto = new UsersDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCpf());
        Wallet wallet = new Wallet();
        wallet.setIdUser(user.getId());
        wallet.setCreatedAt(new Date());
        wallet.setAddress(UUID.randomUUID().toString());     
        walletRepository.save(wallet);
        Balance balance = new Balance();
        balance.setIdWallet(wallet.getId());
        balance.setAmount(BigDecimal.ZERO);
        balance.setIdCripto("5dceac38-be39-4061-8d4c-4fab46628dc4");
        balanceRepository.save(balance);
        return usersDto;
    }
    
        public UserLoginDto login(LoginDto loginDto) {
            Users user = usersRepository.findByEmail(loginDto.email());
            if (user != null && user.getPassword().equals(loginDto.password())) {
                Wallet wallet = walletRepository.findByIdUser(user.getId());
                if (wallet == null) {
                    return null; // Wallet not found
                }
                return new UserLoginDto(user.getId(), user.getName(), user.getEmail(), wallet.getAddress());
            }
            return null;
        }
    
    public UsersDto getUserById(String id) {
        Users user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new UsersDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCpf());
    }

    public UsersDto updateUser(UsersDto usersDto) {
        Users user = usersRepository.findById(usersDto.id()).orElse(null);
        if (user == null) {
            return null;
        }
        user.setName(usersDto.name());
        user.setEmail(usersDto.email());
        user.setPassword(usersDto.password());
        user.setCpf(usersDto.cpf());
        usersRepository.save(user);
        return new UsersDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCpf());
    }

    public boolean deleteUser(String id) {
        if (!usersRepository.existsById(id)) {
            return false;
        }
        usersRepository.deleteById(id);
        return true;
    }

    public List<UsersDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(user -> new UsersDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCpf()))
                .toList();
    }

   
    
}
                       