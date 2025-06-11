package purse.coin.purse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import purse.coin.purse.dto.CryptoDto;
import purse.coin.purse.model.Crypto;
import purse.coin.purse.repository.CryptoRepository;

@Service
public class CryptoService {


    CryptoRepository cryptoRepository;
    public  CryptoService(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }
    
    public CryptoDto postCrypto(CryptoDto cryptoDto) {
        Crypto crypto = new Crypto();
        crypto.setName(cryptoDto.name());
        crypto.setSymbol(cryptoDto.symbol());
        crypto.setValueUSD(cryptoDto.valueUSD());
        Crypto savedCrypto = cryptoRepository.save(crypto);
        return new CryptoDto(
            savedCrypto.getId(),
            savedCrypto.getSymbol(),
            savedCrypto.getName(),
            savedCrypto.getValueUSD()
        );
    }

    public CryptoDto getCryptoById(String id) {
        Crypto crypto = cryptoRepository.findById(id).orElse(null);
        if (crypto == null) {
            return null;
        }
        return new CryptoDto(
            crypto.getId(),
            crypto.getSymbol(),
            crypto.getName(),
            crypto.getValueUSD()
        );
    }

    public CryptoDto updateCrypto(String id, CryptoDto cryptoDto) {
        Crypto crypto = cryptoRepository.findById(id).orElse(null);
        if (crypto == null) {
            return null;
        }
        crypto.setName(cryptoDto.name());
        crypto.setSymbol(cryptoDto.symbol());
        crypto.setValueUSD(cryptoDto.valueUSD());
        Crypto updatedCrypto = cryptoRepository.save(crypto);
        return new CryptoDto(
            updatedCrypto.getId(),
            updatedCrypto.getSymbol(),
            updatedCrypto.getName(),
            updatedCrypto.getValueUSD()
        );
    }

    public void deleteCrypto(String id) {
        cryptoRepository.deleteById(id);
    }

    public List<CryptoDto> getAllCryptos() {
        List<Crypto> cryptos = cryptoRepository.findAll();
        return cryptos.stream()
                .map(crypto -> new CryptoDto(
                        crypto.getId(),
                        crypto.getSymbol(),
                        crypto.getName(),
                        crypto.getValueUSD()))
                .toList();
    }
                
}
