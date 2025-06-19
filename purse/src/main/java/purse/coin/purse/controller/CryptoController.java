package purse.coin.purse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import purse.coin.purse.dto.CryptoDto;
import purse.coin.purse.service.CryptoService;

@RestController
@RequestMapping("/crypto")
@CrossOrigin(origins = "*")
public class CryptoController {

    
    private final CryptoService cryptoService;
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping
    public ResponseEntity<CryptoDto> postCrypto(@RequestBody CryptoDto cryptoDto) {
        CryptoDto savedCrypto = cryptoService.postCrypto(cryptoDto);
        return ResponseEntity.ok(savedCrypto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CryptoDto>> getAllCrypto() {
        List<CryptoDto> cryptoList = cryptoService.getAllCryptos();
        if (cryptoList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cryptoList);   
    }

    
}
