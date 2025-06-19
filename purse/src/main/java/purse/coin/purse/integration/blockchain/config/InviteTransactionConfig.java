package purse.coin.purse.integration.blockchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import feign.Feign;
import feign.slf4j.Slf4jLogger;
import purse.coin.purse.integration.blockchain.InviteTransaction;

@Configuration
public class InviteTransactionConfig {
        @Bean
        @Lazy
       public InviteTransaction inviteTransaction() {
        return Feign.builder()
                .decoder(new feign.jackson.JacksonDecoder())
                .encoder(new feign.jackson.JacksonEncoder())
                .logger(new Slf4jLogger(InviteTransaction.class))
                .logLevel(feign.Logger.Level.FULL)
                .target(InviteTransaction.class, "http://localhost:8000");
    
    
    }
}
